package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.dto.SysAclModuleLevelDto;
import com.mmall.param.AclModuleParam;
import com.mmall.service.ISysAclModuleService;
import com.mmall.service.ISysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限模块的管理
 * Created by Administrator on 2018/3/24 0024.
 */
@Controller
@RequestMapping("sys/aclModule/")
@Slf4j
public class SysAclModuleController {

    @Resource
    private ISysAclModuleService iSysAclModuleService;

    @Resource
    private ISysTreeService iSysTreeService;

    /**
     * 新增权限模块
     * @param aclModuleParam
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam aclModuleParam) {
        JsonData jsonData = iSysAclModuleService.save(aclModuleParam);
        if (jsonData.isSuccess()) {
            return JsonData.success(jsonData.getData());
        }
        return JsonData.fail("新增权限模块失败");
    }

    /**
     * 更新权限模块
     * @param aclModuleParam
     * @return
     */
    @RequestMapping("update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam aclModuleParam) {
        JsonData jsonData = iSysAclModuleService.update(aclModuleParam);
        if (jsonData.isSuccess()) {
            return JsonData.success(jsonData.getData());
        }
        return JsonData.fail("更新权限模块失败");
    }

    /**
     * 获取权限模块树
     * @return
     */
    @RequestMapping("tree.json")
    @ResponseBody
    public JsonData tree() {
        List<SysAclModuleLevelDto> sysAclModuleLevelDtoList = iSysTreeService.aclModuleTree();
        return JsonData.success(sysAclModuleLevelDtoList);
    }

    /**
     * 删除权限模块
     * @param aclModuleId
     * @return
     */
    @RequestMapping("delete.json")
    @ResponseBody
    public JsonData delete(int aclModuleId) {
        return iSysAclModuleService.delete(aclModuleId);
    }

}
