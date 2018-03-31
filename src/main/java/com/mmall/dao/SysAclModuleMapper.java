package com.mmall.dao;

import com.mmall.module.SysAclModule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAclModuleMapper {
    int deleteByPrimaryKey(Integer aclModuleId);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Integer aclModuleId);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    /**
     * 查询权限模块数量
     * @return
     */
    int countAclModule();

    /**
     * 获取当前权限模块的所有子权限模块
     * @param level
     * @return
     */
    List<SysAclModule> getChildSysAclModuleListByLevel(@Param("level") String level);

    /**
     * 批量更新权限模块层级
     * @param sysAclModuleList
     */
    void batchUpdateLevel(@Param("sysAclModuleList") List<SysAclModule> sysAclModuleList);

    /**
     * 获取所有权限模块
     * @return
     */
    List<SysAclModule> getAllAclModule();

    /**
     * 检查当前权限模块是否存在子权限模块
     * @param parentId
     * @return
     */
    int countByParentId(@Param("parentId") Integer parentId);

    /**
     * 检查相同层级下是否存在相同名称的权限模块
     * @param parentId
     * @param sysAclModuleName
     * @param sysAclModuleId
     */
    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("sysAclModuleName") String sysAclModuleName, @Param("sysAclModuleId") Integer sysAclModuleId);
}