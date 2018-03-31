package com.mmall.service;

import com.mmall.common.JsonData;
import com.mmall.param.DeptParam;

/**
 * Created by Administrator on 2018/3/17 0017.
 */
public interface ISysDeptService {

    /**
     * 新增部门
     * @param deptParam
     */
    JsonData save(DeptParam deptParam);

    /**
     * 更新部门
     * @param deptParam
     */
    JsonData update(DeptParam deptParam);

    /**
     * 删除部门
     * @param deptId
     * @return
     */
    JsonData delete(int deptId);

    /**
     * 获取某一部门详情
     * @param deptId
     * @return
     */
    JsonData detail(int deptId);

}
