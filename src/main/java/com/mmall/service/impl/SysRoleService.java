package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.mmall.common.JsonData;
import com.mmall.common.RequestHolder;
import com.mmall.dao.*;
import com.mmall.exception.ParamException;
import com.mmall.module.SysRole;
import com.mmall.module.SysUser;
import com.mmall.param.RoleParam;
import com.mmall.service.ISysRoleService;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/24 0024.
 */
@Service("iSysRoleSevice")
public class SysRoleService implements ISysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    @Resource
    private SysUserMapper sysUserMapper;


    /**
     * 新增角色
     *
     * @param roleParam
     * @return
     */
    @Override
    public JsonData save(RoleParam roleParam) {
        BeanValidator.check(roleParam);
        if (checkExist(roleParam.getName(), roleParam.getId())) {
            throw new ParamException("角色名称已存在");
        }
        SysRole sysRole = SysRole.builder().name(roleParam.getName()).status(roleParam.getStatus()).
                type(roleParam.getType()).remark(roleParam.getRemark()).build();
        sysRole.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysRole.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysRole.setOperatorTime(new Date());
        sysRoleMapper.insertSelective(sysRole);
        return JsonData.success(sysRole);
    }

    /**
     * 更新角色
     *
     * @param roleParam
     * @return
     */
    @Override
    public JsonData update(RoleParam roleParam) {
        BeanValidator.check(roleParam);
        if (checkExist(roleParam.getName(), roleParam.getId())) {
            throw new ParamException("角色名称已存在");
        }
        SysRole before = sysRoleMapper.selectByPrimaryKey(roleParam.getId());
        Preconditions.checkNotNull(before, "待更新的角色不存在");
        SysRole after = SysRole.builder().id(roleParam.getId()).name(roleParam.getName()).status(roleParam.getStatus()).
                type(roleParam.getType()).remark(roleParam.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperatorTime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(after);
        return null;
    }

    /**
     * 获取某一用户的角色
     * @param userId
     * @return
     */
    @Override
    public List<SysRole> getRoleListByUserId(int userId) {
        // 获取某一用户的角色id集合
        List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        List<SysRole> roleList = sysRoleMapper.getByIdList(roleIdList);
        return roleList;
    }

    /**
     * 获取某一权限点所分配的角色
     * @param aclId
     * @return
     */
    @Override
    public List<SysRole> getRoleListByAclId(int aclId) {
        List<Integer> roleIdList = sysRoleAclMapper.getRoleIdListByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        List<SysRole> roleList = sysRoleMapper.getByIdList(roleIdList);
        return roleList;
    }

    /**
     * 根据角色列表获取对应的用户列表
     * @param sysRoleList
     * @return
     */
    @Override
    public List<SysUser> getUserListByRoleList(List<SysRole> sysRoleList) {
        if (CollectionUtils.isEmpty(sysRoleList)) {
            return Lists.newArrayList();
        }
        List<Integer> roleIdList = Lists.newArrayList();
        for (SysRole sysRole : sysRoleList) {
            Integer roleId = sysRole.getId();
            roleIdList.add(roleId);
        }
        // 根据角色id列表获取用户id列表
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        // 返回对应的用户列表
        return sysUserMapper.getByUserIdList(userIdList);

    }

    /**
     * 获取所有的角色
     * @return
     */
    @Override
    public List<SysRole> getAll() {
        return sysRoleMapper.getAll();
    }

    /**
     * 检查是否存在相同的角色
     * @param name
     * @param id
     * @return
     */
    private boolean checkExist(String name, Integer id) {
        return sysRoleMapper.countByNameId(name, id) > 0;
    }
}
