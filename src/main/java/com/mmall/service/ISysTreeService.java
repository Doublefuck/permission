package com.mmall.service;

import com.mmall.dto.DeptLevelDto;
import com.mmall.dto.SysAclModuleLevelDto;

import java.util.List;

/**
 * 计算组装树状结构
 * Created by Administrator on 2018/3/22 0022.
 */
public interface ISysTreeService {

    /**
     * 组装部门树
     * @return
     */
    List<DeptLevelDto> deptTree();

    /**
     * 组装权限模块树
     * @return
     */
    List<SysAclModuleLevelDto> aclModuleTree();

    /**
     * 获取角色权限树数据
     * @param roleId
     * @return
     */
    List<SysAclModuleLevelDto> roleTree(Integer roleId);

    /**
     * 获取某一用户权限信息
     * @param userId
     * @return
     */
    List<SysAclModuleLevelDto> userAclTree(int userId);

}
