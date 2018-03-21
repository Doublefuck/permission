package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.param.DeptParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/3/17 0017.
 */
@Controller
@RequestMapping("/sys/dept/")
@Slf4j
public class SysDeptController {

    @RequestMapping("save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam deptParam) {
        return null;
    }

}
