package com.mmall.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.common.JsonData;
import com.mmall.module.SysUser;
import com.mmall.param.RoleParam;
import com.mmall.service.*;
import com.mmall.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    @Resource
    private ISysRoleAclService iSysRoleAclService;

    @Resource
    private ISysRoleUserService iSysRoleUserService;

    @Resource
    private ISysUserService iSysUserService;

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

    /**
     * 更新角色对应的权限点
     * @param roleId
     * @param sclIds
     * @return
     */
    @RequestMapping("changeAcls.json")
    @ResponseBody
    public JsonData changeAcls(@RequestParam("roleId") Integer roleId, @RequestParam("aclIds") String sclIds) {
        List<Integer> aclIdList = StringUtil.splitToListInt(sclIds);
        iSysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonData.success(roleTree(roleId));
    }

    /**
     * 更新角色对应的用户信息
     * @param roleId
     * @param userIds
     * @return
     */
    @RequestMapping("changeUsers.json")
    @ResponseBody
    public JsonData changeUsers(@RequestParam("roleId") Integer roleId, @RequestParam("userIds") String userIds) {
        List<Integer> userIdList = StringUtil.splitToListInt(userIds);
        iSysRoleUserService.changeRoleUsers(roleId, userIdList);
        return JsonData.success(roleTree(roleId));
    }

    /**
     * 根据roleId获取所有对应的用户信息
     * 同时获取当前角色下未包含的正常状态用户
     * @param roleId
     * @return
     */
    @RequestMapping("users.json")
    @ResponseBody
    public JsonData users(@RequestParam("roleId") int roleId) {
        // 获取roleId对应的用户集合
        JsonData jsonData = iSysRoleUserService.getUserListByRoleId(roleId);
        List<SysUser> selectedSysUserList = Lists.newArrayList();
        if (jsonData.isSuccess()) {
            selectedSysUserList = (List<SysUser>) jsonData.getData();
        }
        // 获取所有的用户信息
        List<SysUser> sysUserList = iSysUserService.getAll();
        // 获取不在当前角色下的用户列表
        List<SysUser> unselectedUserList = Lists.newArrayList();
        for (SysUser sysUser : sysUserList) {
            // 用户状态可用且不在当前角色下
            if (sysUser.getStatus() == 1 && !selectedSysUserList.contains(sysUser)) {
                unselectedUserList.add(sysUser);
            }
        }
        Map<String, List<SysUser>> map = Maps.newHashMap();
        map.put("selected", selectedSysUserList);
        map.put("unselected", unselectedUserList);
        return JsonData.success(map);
    }
}
