package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.module.SysAcl;
import com.mmall.service.ISysCoreService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户权限、角色权限关系管理
 * Created by Administrator on 2018/3/25 0025.
 */
@Service("iSysCoreService")
public class SysCoreService implements ISysCoreService {

    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    /**
     * 获取当前用户的权限点
     * @return
     */
    @Override
    public List<SysAcl> getCurrentUserAclList() {
        int userId = RequestHolder.getCurrentUser().getId();
        // 如果用户是超级管理员，则返回所有的权限点
        if (isSuperAdmin()) {
            return sysAclMapper.getAll();
        }
        // 获取当前用户的所有角色id
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }
        // 当前用户的角色列表对应的权限id列表
        List<Integer> userRoleAclIdList = sysRoleAclMapper.getAclIdByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userRoleAclIdList)) {
            return Lists.newArrayList();
        }
        // 根据用户所有的权限id获取对应的权限点集合
        List<SysAcl> sysAclList = sysAclMapper.getByIdList(userRoleAclIdList);
        return sysAclList;
    }

    /**
     * 获取某个角色的已分配权限点
     * @param roleId
     * @return
     */
    @Override
    public List<SysAcl> getRoleAclList(int roleId) {
        // 获取当前角色的所有权限点id
        List<Integer> roleAclIdList = sysRoleAclMapper.getAclIdByRoleIdList(Lists.newArrayList(roleId));
        if (CollectionUtils.isEmpty(roleAclIdList)) {
            return Lists.newArrayList();
        }
        // 根据权限点id获取对应的权限点对象集合
        List<SysAcl> sysAclList = sysAclMapper.getByIdList(roleAclIdList);
        return sysAclList;
    }

    /**
     * 判断用户是否是超级管理员
     * @return
     */
    public boolean isSuperAdmin() {
        return true;

    }
}
