package com.mmall.service;

import com.mmall.common.JsonData;
import com.mmall.param.AclParam;

/**
 * Created by Administrator on 2018/3/24 0024.
 */
public interface ISysAclService {

    /**
     * 新增权限点
     * @param aclParam
     * @return
     */
    JsonData save(AclParam aclParam);

    /**
     * 更新权限点
     * @param aclParam
     * @return
     */
    JsonData update(AclParam aclParam);

    /**
     * 根据权限模块id获取所有权限点
     * @param aclModuleId
     * @return
     */
    JsonData list(Integer aclModuleId);

}
