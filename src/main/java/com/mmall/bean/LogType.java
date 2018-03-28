package com.mmall.bean;

/**
 * 日志操作对象类型
 * Created by Administrator on 2018/3/26 0026.
 */
public interface LogType {

    // 部门更新
    int TYPE_DEPT = 1;

    // 用户更新
    int TYPE_USER = 2;

    // 权限模块更新
    int TYPE_ACL_MODULE = 3;

    // 权限更新
    int TYPE_ACL = 4;

    // 角色更新
    int TYPE_ROLE = 5;

    // 角色权限更新
    int TYPE_ROLE_ACL = 6;

    // 角色用户更新
    int TYPE_ROLE_USER = 7;
}
