package com.mmall.dao;

import com.mmall.module.SysRoleUser;
import com.mmall.module.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    SysRoleUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleUser record);

    int updateByPrimaryKey(SysRoleUser record);

    List<Integer> getRoleIdListByUserId(@Param("userId") int userId);

    List<Integer> getUserIdListByRoleId(@Param("roleId") int roleId);

    void deleteByRoleId(@Param("roleId") int roleId);

    void batchInsert(@Param("sysRoleUserList") List<SysRoleUser> sysRoleUserList);

    List<Integer> getUserIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);
}