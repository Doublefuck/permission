package com.mmall.controller;

import com.mmall.common.ApplocationContextHelper;
import com.mmall.common.JsonData;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.exception.PermissionException;
import com.mmall.module.SysAclModule;
import com.mmall.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/3/16 0016.
 */
@Controller
@RequestMapping("/test/")
@Slf4j
public class TestController {

    @RequestMapping("hello.json")
    @ResponseBody
    public JsonData hello() {
        log.info("hello");
        //throw new PermissionException("permission exception");
        // return JsonData.success("hello permission");
        SysAclModuleMapper sysAclModuleMapper = ApplocationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.obj2String(aclModule));
        return JsonData.success("test true");
    }


}
