package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.bean.CacheKeyConstants;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.module.SysAcl;
import com.mmall.module.SysUser;
import com.mmall.service.ISysCoreService;
import com.mmall.util.JsonMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

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

    @Resource
    private SysCacheService sysCacheService;

    /**
     * 获取当前用户的权限点
     * @return
     */
    @Override
    public List<SysAcl> getCurrentUserAclList() {
        int userId = RequestHolder.getCurrentUser().getUserId();
        // 如果用户是超级管理员，则返回所有的权限点
        if (isSuperAdmin()) {
            return sysAclMapper.getAll();
        }
        // 获取当前用户的所有角色id
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }
        // 当前用户的角色id列表对应的权限id列表
        List<Integer> userRoleAclIdList = sysRoleAclMapper.getAclIdByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userRoleAclIdList)) {
            return Lists.newArrayList();
        }
        // 根据用户所有的权限id获取对应的权限点集合
        List<SysAcl> sysAclList = sysAclMapper.getAclByAclIdList(userRoleAclIdList);
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
        List<SysAcl> sysAclList = sysAclMapper.getAclByAclIdList(roleAclIdList);
        return sysAclList;
    }

    /**
     * 获取某个用户的权限点
     * @param userId
     * @return
     */
    @Override
    public List<SysAcl> getUserAclList(int userId) {
        if (isSuperAdmin()) {
            return sysAclMapper.getAll();
        }
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (org.apache.commons.collections.CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }
        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdByRoleIdList(userRoleIdList);
        if (org.apache.commons.collections.CollectionUtils.isEmpty(userAclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getAclByAclIdList(userAclIdList);
    }

    /**
     * 判断用户是否是超级管理员
     * TODO 此处是自己随便定义的超级管理员规则，实际需要根据项目需求变化，
     * 可以是配置文件获取，也可以指定某个用户或者某个角色
     * @return
     */
    public boolean isSuperAdmin() {
        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser.getEmail().contains("admin")) {
            return true;
        }
        return true;
    }

    /**
     *
     * @param url
     * @return
     */
    public boolean hasUrlAcl(String url) {
        if (isSuperAdmin()) {
            return true;
        }
        // 获取url对应的所有权限点
        List<SysAcl> aclList = sysAclMapper.getByUrl(url);
        if (CollectionUtils.isEmpty(aclList)) {
            return true;
        }
        // 从缓存中获取用户权限点列表
        // List<SysAcl> userAclIdList = getCurrentUserAclListFromCache();
        List<SysAcl> userAclIdList = getCurrentUserAclList();
        Set<Integer> userAclIdSet = Sets.newHashSet();
        for (SysAcl sysAcl : userAclIdList) {
            Integer userAclId = sysAcl.getAclId();
            userAclIdSet.add(userAclId);
        }
        // 规则：只要有一个权限点
        boolean hasValidAcl = false; // 权限点是否有效
        for (SysAcl sysAcl : aclList) {
            // 判断一个用户是否具有某个权限点的访问权限
            if (sysAcl == null || sysAcl.getStatus() != 1) { // 权限点无效
                continue;
            }
            hasValidAcl = true;
            if (userAclIdSet.contains(sysAcl.getAclId())) {
                return true;
            }
            if (!hasValidAcl) {
                return true;
            }
        }
        return false;
    }


    public List<SysAcl> getCurrentUserAclListFromCache() {
        int userId = RequestHolder.getCurrentUser().getUserId();
        String cacheValue = sysCacheService.getFromCache(CacheKeyConstants.USER_ACLS, String.valueOf(userId));
        if (StringUtils.isBlank(cacheValue)) {
            List<SysAcl> aclList = getCurrentUserAclList();
            if (CollectionUtils.isNotEmpty(aclList)) {
                sysCacheService.saveCache(JsonMapper.obj2String(aclList), 600, CacheKeyConstants.USER_ACLS);
            }
            return aclList;
        }
        return JsonMapper.string2Obj(cacheValue, new TypeReference<List<SysAcl>>() {
        });
    }
}
