package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.param.AclParam;
import com.mmall.service.ISysAclService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 权限点管理
 * Created by Administrator on 2018/3/24 0024.
 */
@Controller
@RequestMapping("/sys/acl/")
public class SysAclController {

    @Resource
    private ISysAclService iSysAclService;

    /**
     * 新增权限点
     * @param aclParam
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public JsonData save(AclParam aclParam) {
        return iSysAclService.save(aclParam);
    }

    /**
     * 更新权限点
     * @param aclParam
     * @return
     */
    @RequestMapping("update.json")
    @ResponseBody
    public JsonData update(AclParam aclParam) {
        return iSysAclService.update(aclParam);
    }

    /**
     * 根据权限模块id获取所有权限点
     * TODO 分页操作
     * @param aclModuleId
     * @return
     */
    @RequestMapping("list.json")
    @ResponseBody
    public JsonData list(Integer aclModuleId) {
        return iSysAclService.list(aclModuleId);
    }
}
