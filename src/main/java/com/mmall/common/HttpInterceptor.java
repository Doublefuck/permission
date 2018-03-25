package com.mmall.common;

import com.mmall.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * http请求拦截器
 * Created by Administrator on 2018/3/17 0017.
 */
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {

    private static String START_TIME = "request_start";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURL().toString(); //请求地址
        Map parameters = request.getParameterMap(); // 请求参数
        long start = System.currentTimeMillis();
        request.setAttribute(START_TIME, start);
        log.info("request start,url:{}, params:{}", url, JsonMapper.obj2String(parameters));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String url = request.getRequestURL().toString(); //请求地址
        Map parameters = request.getParameterMap(); // 请求参数
        long start = (Long) request.getAttribute(START_TIME);
        long end= System.currentTimeMillis();
        log.info("request finished,url:{}, params:{}, total time:{}", url, JsonMapper.obj2String(parameters), end - start);
        RequestHolder.remove(); // 请求完成移除ThreadLocal
        log.info("remove ThreadLocal");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.remove(); // 请求结束移除ThreadLocal
        log.info("after remove ThreadLocal");
    }
}
