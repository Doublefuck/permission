package com.mmall.service;

import com.mmall.common.JsonData;
import com.mmall.param.AclModuleParam;

/**
 * Created by Administrator on 2018/3/24 0024.
 */
public interface ISysAclModuleService {

    /**
     * 新增权限模块
     * @param aclModuleParam
     */
    JsonData save(AclModuleParam aclModuleParam);

    /**
     * 更新权限模块
     * @param aclModuleParam
     */
    JsonData update(AclModuleParam aclModuleParam);

    /**
     * 删除权限模块
     * @param aclModuleId
     * @return
     */
    JsonData delete(int aclModuleId);
}
