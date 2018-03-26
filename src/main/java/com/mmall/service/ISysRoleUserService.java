package com.mmall.service;

import com.mmall.common.JsonData;
import com.mmall.module.SysUser;

import java.util.List;

/**
 * Created by Administrator on 2018/3/25 0025.
 */
public interface ISysRoleUserService {

    /**
     * 根据roleId获取所有对应的用户
     * @param roleId
     * @return
     */
    JsonData getUserListByRoleId(int roleId);

    /**
     * 修改当前角色下的用户信息
     * @param roleId
     * @param userIds
     * @return
     */
    JsonData changeRoleUsers(int roleId, List<Integer> userIds);

}
