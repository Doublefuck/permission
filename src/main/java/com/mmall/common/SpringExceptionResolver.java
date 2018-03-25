package com.mmall.common;

import com.mmall.exception.ParamException;
import com.mmall.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 * Created by Administrator on 2018/3/16 0016.
 */
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        String url = request.getRequestURL().toString(); // 获取请求地址
        ModelAndView modelAndView = null;
        JsonData jsonData = null;
        String defaultMsg = "system error"; // 默认异常信息
        // .json或者.page结尾
        if (url.endsWith(".json")) {
            // 异常属于自定义异常
            if (e instanceof PermissionException || e instanceof ParamException) {
                // 封装异常的信息
                jsonData = JsonData.fail(e.getMessage());
                modelAndView = new ModelAndView("jsonView", jsonData.toMap());
            } else {
                // 使用自定义的默认异常信息
                log.error("unknown json exception,url:{}", url, e);
                jsonData = JsonData.fail(defaultMsg);
                modelAndView = new ModelAndView("jsonView", jsonData.toMap());
            }
        } else if (url.endsWith(".page")) {
            log.error("unknown page exception,url:{}", url, e);
            jsonData = JsonData.fail(defaultMsg);
            modelAndView = new ModelAndView("exception", jsonData.toMap());
        } else {
            log.error("unknown exception,url:{}", url, e);
            jsonData = JsonData.fail(defaultMsg);
            modelAndView = new ModelAndView("jsonView", jsonData.toMap());
        }
        return modelAndView;
    }
}
