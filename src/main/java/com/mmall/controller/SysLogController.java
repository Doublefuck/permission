package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.module.SysLogWithBLOBs;
import com.mmall.param.SearchLogParam;
import com.mmall.service.ISysLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 记录权限操作的日志
 * Created by Administrator on 2018/3/26 0026.
 */
@Controller
@RequestMapping("/sys/log/")
public class SysLogController {

    @Resource
    private ISysLogService iSysLogService;

    /**
     * 动态搜索日志信息
     * TODO 分页操作
     * @param searchLogParam
     * @return
     */
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(SearchLogParam searchLogParam) {
        List<SysLogWithBLOBs> sysLogWithBLOBsList = iSysLogService.searchLog(searchLogParam);
        return JsonData.success(sysLogWithBLOBsList);
    }

    /**
     * 日志还原数据操作
     * @param id
     * @return
     */
    @RequestMapping("/recover.json")
    @ResponseBody
    public JsonData recover(int id) {
        iSysLogService.recover(id);
        return JsonData.success();
    }
}
