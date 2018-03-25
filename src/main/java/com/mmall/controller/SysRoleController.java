package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.param.RoleParam;
import com.mmall.service.ISysRoleService;
import com.mmall.service.ISysTreeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 角色管理
 * Created by Administrator on 2018/3/24 0024.
 */
@Controller
@RequestMapping("/sys/role/")
public class SysRoleController {

    @Resource
    private ISysRoleService iSysRoleService;

    @Resource
    private ISysTreeService iSysTreeService;

    /**
     * 新增角色
     * @param roleParam
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public JsonData save(RoleParam roleParam) {
        return iSysRoleService.save(roleParam);
    }

    /**
     * 更新角色
     * @param roleParam
     * @return
     */
    @RequestMapping("update.json")
    @ResponseBody
    public JsonData update(RoleParam roleParam) {
        return iSysRoleService.update(roleParam);
    }

    /**
     * 获取所有角色
     * @return
     */
    @RequestMapping("list.json")
    @ResponseBody
    public JsonData list() {
        return JsonData.success(iSysRoleService.getAll());
    }

    /**
     * 根据角色id获取角色的权限树
     * 角色的权限树，包含权限模块和权限点组成的树状结构数据
     * @return
     */
    @RequestMapping("roleTree.json")
    @ResponseBody
    public JsonData roleTree(@RequestParam("roleId") Integer roleId) {
        return JsonData.success(iSysTreeService.roleTree(roleId));
    }
}
