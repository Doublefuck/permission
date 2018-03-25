package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.module.SysUser;
import com.mmall.service.ISysUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 前台用户接口
 * Created by Administrator on 2018/3/22 0022.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private ISysUserService iSysUserSevice;

    /**
     * 前台用户登录接口
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping("/login.json")
    @ResponseBody
    public JsonData login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        SysUser sysUser = iSysUserSevice.findByKeyword(username);
        String errMsg = ""; // 错误提示信息
        String ret = request.getParameter("ret"); // 从别的页面跳转到登录页面携带的地址

        if (StringUtils.isBlank(username)) {
            errMsg = "用户名不可以为空";
        } else if (StringUtils.isBlank(password)) {
            errMsg = "密码不可以为空";
        } else if (sysUser == null) {
            errMsg = "查询不到指定的用户";
        } else if (sysUser.getPassword().equals(MD5Util.encrypt(password))) {
            errMsg = "用户名或密码错误";
        } else if (sysUser.getStatus() != 1) {
            errMsg = "用户被冻结，请联系管理员";
        } else {
            // 登录成功
            sysUser.setPassword(""); // 隐藏密码
            request.getSession().setAttribute("user", sysUser);
            return JsonData.success(sysUser, "登陆成功");
            // 页面重定向
//            if (StringUtils.isNotBlank(ret)) {
//                response.sendRedirect(ret);
//            } else {
//                response.sendRedirect("/admin/index.page");
//            }
        }
//        request.setAttribute("error", errMsg);
//        request.setAttribute("username", username);
//        if (StringUtils.isNotBlank(ret)) {
//            response.sendRedirect(ret);
//        }
//        String path = "signin.jsp";
//        request.getRequestDispatcher(path).forward(request, response);
        return JsonData.fail(errMsg);
    }

    /**
     * 退出登录
     * @param request
     * @param response
     */
    @RequestMapping("/logout.json")
    @ResponseBody
    public JsonData logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate(); // 移除session中存储的信息
//        String path = "signin.jsp";
//        response.sendRedirect(path);
        return JsonData.success("退出登录状态成功");
    }


}
