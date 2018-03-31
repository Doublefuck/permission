package com.mmall.controller;

import com.google.common.collect.Maps;
import com.mmall.common.JsonData;
import com.mmall.module.SysUser;
import com.mmall.param.UserParam;
import com.mmall.service.ISysRoleService;
import com.mmall.service.ISysTreeService;
import com.mmall.service.ISysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 后台用户管理
 * Created by Administrator on 2018/3/22 0022.
 */
@RequestMapping("/sys/user")
@Controller
public class SysUserController {

    @Resource
    private ISysUserService iSysUserService;

    @Resource
    private ISysTreeService iSysTreeService;

    @Resource
    private ISysRoleService iSysRoleService;

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
            return JsonData.fail("用户未登录，请先登录xxx");
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

    /**
     * 获取所有用户信息
     * @return
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public JsonData list() {
        List<SysUser> sysUserList = iSysUserService.getAll();
        return JsonData.success(sysUserList);
    }

    /**
     * 获取单一用户权限信息
     * @param userId
     * @return
     */
    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(int userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls", iSysTreeService.userAclTree(userId));
        map.put("roles", iSysRoleService.getRoleListByUserId(userId));
        return JsonData.success(map);
    }
}
