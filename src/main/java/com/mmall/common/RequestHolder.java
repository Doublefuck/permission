package com.mmall.common;

import com.mmall.module.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * ThreadLocal实现线程局部变量的模式
 * ThreaLocal相当于一个Map，key对应的是当前的线程，value代表线程访问的变量资源
 * Created by Administrator on 2018/3/24 0024.
 */
public class RequestHolder {

    // 存放当前登录的用户信息
    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<SysUser>();

    // 存放request请求
    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

    public static void add(SysUser sysUser) {
        userHolder.set(sysUser);
    }

    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static SysUser getCurrentUser() {
        return userHolder.get();
    }

    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
    }

}
