package com.mmall.service;

import com.mmall.common.JsonData;
import com.mmall.module.SysRole;
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
}
