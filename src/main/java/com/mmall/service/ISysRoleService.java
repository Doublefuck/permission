package com.mmall.service;

import com.mmall.common.JsonData;
import com.mmall.module.SysRole;
import com.mmall.module.SysUser;
import com.mmall.param.RoleParam;

import java.util.List;

/**
 * Created by Administrator on 2018/3/24 0024.
 */
public interface ISysRoleService {

    /**
     * 新增角色
     * @param roleParam
     * @return
     */
    JsonData save(RoleParam roleParam);

    /**
     * 更新角色
     * @param roleParam
     * @return
     */
    JsonData update(RoleParam roleParam);

    /**
     * 获取所有的角色
     * @return
     */
    List<SysRole> getAll();

    /**
     * 获取当前用户已分配的角色
     * @param userId
     * @return
     */
    List<SysRole> getRoleListByUserId(int userId);

    /**
     * 获取某一权限点所分配的角色
     * @param aclId
     * @return
     */
    List<SysRole> getRoleListByAclId(int aclId);

    /**
     * 根据角色列表获取对应的用户列表
     * @param sysRoleList
     * @return
     */
    List<SysUser> getUserListByRoleList(List<SysRole> sysRoleList);
}
