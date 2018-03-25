package com.mmall.service;

import com.mmall.common.JsonData;
import com.mmall.module.SysUser;
import com.mmall.param.UserParam;
import org.springframework.stereotype.Service;

/**
 * 后台用户管理
 * Created by Administrator on 2018/3/22 0022.
 */
@Service("iSysUserService")
public interface ISysUserService {

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
     * @param username
     * @param password
     * @return
     */
    JsonData findByParam(String username, String password);

    /**
     * 根据关键字查询信息，可以是用户名、手机号、邮箱等
     * @param keyword
     * @return
     */
    SysUser findByKeyword(String keyword);
}
