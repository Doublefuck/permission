package com.mmall.service;

import com.mmall.common.JsonData;

import java.util.List;

/**
 * Created by Administrator on 2018/3/25 0025.
 */
public interface ISysRoleAclService {

    /**
     * 更改角色对应的权限点信息
     * @param roleId
     * @param aclIdList
     * @return
     */
    JsonData changeRoleAcls(Integer roleId, List<Integer> aclIdList);

}
