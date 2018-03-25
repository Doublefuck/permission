package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.module.SysUser;
import com.mmall.param.UserParam;
import com.mmall.service.ISysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 后台用户管理
 * Created by Administrator on 2018/3/22 0022.
 */
@RequestMapping("/sys/user")
@Controller
public class SysUserController {

    @Resource
    private ISysUserService iSysUserService;

    /**
     * 新增用户
     * @param userParam
     * @param session
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveUser(HttpSession session, UserParam userParam) {
        SysUser sysUser = (SysUser) session.getAttribute("user");
        if (sysUser == null) {
            return JsonData.fail("用户未登录，请先登录");
        }
        iSysUserService.save(userParam);
        return JsonData.success();
    }

    /**
     * 更新用户信息
     * @param userParam
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam userParam) {
        iSysUserService.update(userParam);
        return JsonData.success();
    }
}
