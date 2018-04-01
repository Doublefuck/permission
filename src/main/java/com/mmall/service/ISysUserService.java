package com.mmall.service;

import com.mmall.common.JsonData;
import com.mmall.module.SysUser;
import com.mmall.param.UserParam;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台用户管理
 * Created by Administrator on 2018/3/22 0022.
 */
@Service("iSysUserService")
public interface ISysUserService {

    /**
     * 用户注册
     * @param sysUser
     * @return
     */
    JsonData register(SysUser sysUser);

    /**
     * 新增用户
     * @param userParam
     */
    void save(UserParam userParam);

    /**
     * 更新用户信息
     * @param userParam
     */
    void update(UserParam userParam);

    /**
     * 用户登录校验
     * @param keyword
     * @return
     */
    JsonData loginByKeyword(String keyword);

    /**
     * 获取所有用户信息列表
     * @return
     */
    List<SysUser> getAll();
}
