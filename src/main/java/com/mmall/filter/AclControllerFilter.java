package com.mmall.filter;

import com.google.common.collect.Sets;
import com.mmall.common.ApplicationContextHelper;
import com.mmall.common.RequestHolder;
import com.mmall.module.SysUser;
import com.mmall.service.impl.SysCoreService;
import com.mmall.util.JsonMapper;
import com.mmall.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 权限过滤器
 * Created by Administrator on 2018/3/25 0025.
 */
@Slf4j
public class AclControllerFilter implements Filter {

    private static Set<String> exclusionUrlSet = Sets.newHashSet();

    /**
     * 获取过滤url地址
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String exclusionUrls = filterConfig.getInitParameter("exclusionUrls");
        List<String> exclusionUrlsList = StringUtil.splitToListString(exclusionUrls);
        exclusionUrlSet = Sets.newHashSet(exclusionUrlsList);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 获取请求地址
        String servletPath = request.getServletPath();
        Map requestMap = request.getParameterMap();
        // 处理白名单，URL在白名单中则不做过滤
        if (exclusionUrlSet.contains(servletPath)) {
            filterChain.doFilter(request, response);
            return;
        }
        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser == null) {
            // 未登录拒绝访问

            log.info("someone visit {}, but no login, parameters:{}", servletPath, JsonMapper.obj2String(requestMap));
            return;
        }

        SysCoreService sysCoreService = ApplicationContextHelper.popBean(SysCoreService.class);
        if (!sysCoreService.hasUrlAcl(servletPath)) {
            // 没有权限访问url

            log.info("{} visit {}, but no login, parameter:{}", servletPath, requestMap);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
