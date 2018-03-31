package com.mmall.dao;

import com.mmall.module.SysRoleAcl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleAclMapper {
    int deleteByPrimaryKey(Integer roleAclId);

    int insert(SysRoleAcl record);

    int insertSelective(SysRoleAcl record);

    SysRoleAcl selectByPrimaryKey(Integer roleAclId);

    int updateByPrimaryKeySelective(SysRoleAcl record);

    int updateByPrimaryKey(SysRoleAcl record);

    /**
     * 获取某一权限点所分配的所有角色id
     * @param aclId
     * @return
     */
    List<Integer> getRoleIdListByAclId(@Param("aclId") int aclId);

    /**
     * 根据角色id列表获取对应的权限点id列表
     * @param roleIdList
     * @return
     */
    List<Integer> getAclIdByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

    /**
     * 根据角色id删除对应的权限点
     * @param roleId
     */
    void deleteAclByRoleId(@Param("roleId") Integer roleId);

    /**
     * 批量新增角色权限点
     * @param sysRoleAclList
     */
    void batchInsert(@Param("sysRoleAclList") List<SysRoleAcl> sysRoleAclList);
}