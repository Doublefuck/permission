package com.mmall.interceptor;

import com.mmall.common.JsonData;
import com.mmall.common.RequestHolder;
import com.mmall.module.SysUser;
import com.mmall.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * 登录拦截器
 * Created by Administrator on 2018/3/24 0024.
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private boolean isLogin = false;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

//        String servletPath = request.getServletPath();
//
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
//        if (sysUser == null) {
//
//        }
        // return super.preHandle(request, response, handler);
        log.info("preHandle");

        // 重置response的属性
        response.reset(); // 重置response
        response.setCharacterEncoding("utf-8"); // 设置重置后的response的编码
        response.setContentType("application/json;charset=utf-8"); // 设置响应类型
        PrintWriter out = null; // 重新获取out对象
        try {
            if (sysUser == null) {
                out = response.getWriter();
            }
        } catch (IOException e) {
            log.error("获取out对象异常", e.toString());
        }

        // 请求中Controller中的方法名和类名
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBean().getClass().getSimpleName();

        String uri = request.getRequestURI();
        log.info("request uri:{}", uri);

        // 解析参数，主要为了调试
        StringBuffer requestParamBuffer = new StringBuffer();
        Map paramMap = request.getParameterMap();
        Iterator iterator = paramMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String mapKey = (String) entry.getKey();
            String mapValue = StringUtils.EMPTY; // 空字符串
            Object obj = entry.getValue(); // 会返回数组
            if (obj instanceof String[]) {
                String[] strs = (String[]) obj;
                mapValue = Arrays.toString(strs);
            }
            requestParamBuffer.append(mapKey).append("=").append(mapValue);
        }

        // 硬编码判断是否是登录请求和登录执行的方法
        // 如果拦截器遇到登录的请求，放过该请求，直接允许其进入Controller
        // 此处可以将dispatcher-servlet.xml中配置的登录拦截器去掉
//        if (StringUtils.equals(className, "UserManageController") && StringUtils.equals(methodName, "login")) {
//            log.info("权限拦截器拦截到请求, className:{}, methodName:{}", className, methodName);
//            // 如果拦截到登录请求，则不打印参数（防止账号密码泄露）
//            return true;
//        }

        // 打印拦截的请求信息
        log.info("权限拦截器拦截到请求, className:{}, methodName:{}", className, methodName);

        // 验证用户登录信息
        // SysUser user = null;
        //String loginToken = CookieUtil.readLoginToken(request);
        //if (StringUtils.isNotEmpty(loginToken)) {
            //String userJsonStr = RedisShardedPoolUtil.getKey(loginToken);
            //user = JsonUtil.string2Obj(userJsonStr, User.class);
        //}

        // 用户为空
        if (sysUser == null) {
            out.print(JsonMapper.obj2String(JsonData.fail("用户未登录，请先登录")));
            out.flush(); // 将out流数据清空
            out.close(); // 关闭out流
            return false;
        }
//        out.flush();
//        out.close();
        // 将用户信息放入到ThreadLocal中
        RequestHolder.add(sysUser);
        // 将请求信息放入到ThreadLocal中
        RequestHolder.add(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
