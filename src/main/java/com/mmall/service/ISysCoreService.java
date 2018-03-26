package com.mmall.service;

import com.mmall.module.SysAcl;

import java.util.List;

/**
 * 获取相关角色或者用户的权限
 * Created by Administrator on 2018/3/25 0025.
 */
public interface ISysCoreService {

    /**
     * 获取当前用户的权限点
     * @return
     */
    List<SysAcl> getCurrentUserAclList();

    /**
     * 获取某个角色的权限点
     * @param roleId
     * @return
     */
    List<SysAcl> getRoleAclList(int roleId);

    /**
     * 获取某个用户的权限点
     * @param userId
     * @return
     */
    List<SysAcl> getUserAclList(int userId);


}
