package com.mmall.service.impl;

import com.mmall.param.DeptParam;
import com.mmall.service.ISysDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/3/17 0017.
 */
@Service("iSysDeptService")
public class SysDeptService implements ISysDeptService {

    @Resource
    private ISysDeptService iSysDeptService;

    /**
     * 新增部门
     * @param deptParam
     */
    @Override
    public void save(DeptParam deptParam) {
        iSysDeptService.save(deptParam);
    }
}
