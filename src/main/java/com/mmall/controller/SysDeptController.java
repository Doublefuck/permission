package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.dto.DeptLevelDto;
import com.mmall.param.DeptParam;
import com.mmall.service.ISysDeptService;
import com.mmall.service.ISysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门管理
 * Created by Administrator on 2018/3/17 0017.
 */
@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

    @Resource
    private ISysDeptService iSysDeptService;

    @Resource
    private ISysTreeService iSysTreeService;

    @RequestMapping("/page.json")
    public ModelAndView page() {
        return new ModelAndView("dept");
    }

    /**
     * 新增部门
     * @param deptParam
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam deptParam) {
        iSysDeptService.save(deptParam);
        return JsonData.success();
    }

    /**
     * 获取部门列表并组装部门树
     * @return
     */
    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree() {
        List<DeptLevelDto> deptLevelDtoList = iSysTreeService.deptTree();
        return JsonData.success(deptLevelDtoList);
    }

    /**
     * 更新部门
     * 更新当前部门及其子部门
     * @param deptParam
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam deptParam) {
        iSysDeptService.update(deptParam);
        return JsonData.success();
    }

    /**
     * 删除部门
     * @param deptId
     * @return
     */
    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData deleteDept(@RequestParam("deptId") int deptId) {
        return iSysDeptService.delete(deptId);
    }

}
