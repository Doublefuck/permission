package com.mmall.dao;

import com.mmall.module.SysAcl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAclMapper {
    int deleteByPrimaryKey(Integer aclId);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(Integer aclId);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);

    /**
     * 获取当前的所有权限点
     * @return
     */
    List<SysAcl> getAll();

    /**
     * 检查当前权限模块下是否存在权限点
     * @param aclModuleId
     * @return
     */
    int countByAclModuleId(@Param("aclModuleId") int aclModuleId);

    /**
     * 检测相同权限模块下是否存在相同名称的权限点
     * @param aclModuleId
     * @param aclName
     * @param aclId
     * @return
     */
    int countByNameAndAclModuleId(@Param("aclModuleId") Integer aclModuleId, @Param("aclName") String aclName, @Param("aclId") Integer aclId);

    /**
     * 获取当前所有状态的权限点数量
     * @return
     */
    int countAcl();

    /**
     * 根据权限模块id获取对应的权限点
     * @param aclModuleId
     * @return
     */
    List<SysAcl> getByAclModuleId(@Param("aclModuleId") Integer aclModuleId);

    /**
     * 根据权限点id列表获取对应的权限点集合
     * @param aclIdList
     * @return
     */
    List<SysAcl> getAclByAclIdList(@Param("aclIdList") List<Integer> aclIdList);

    /**
     * 获取url对应的所有权限点
     * @param url
     * @return
     */
    List<SysAcl> getByUrl(@Param("url") String url);
}