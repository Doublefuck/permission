package com.mmall.dao;

import com.mmall.module.SysRoleUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleUserMapper {
    int deleteByPrimaryKey(Integer roleUserId);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    SysRoleUser selectByPrimaryKey(Integer roleUserId);

    int updateByPrimaryKeySelective(SysRoleUser record);

    int updateByPrimaryKey(SysRoleUser record);

    /**
     * 根据角色id获取对应的用户id
     * @param roleIdList
     * @return
     */
    List<Integer> getUserIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

    /**
     * 获取当前用户的所有角色id
     * @param userId
     * @return
     */
    List<Integer> getRoleIdListByUserId(@Param("userId") Integer userId);

    /**
     * 根据角色id获取角色对应的所有用户id列表
     * @param roleId
     * @return
     */
    List<Integer> getUserIdListByRoleId(@Param("roleId") Integer roleId);

    /**
     * 根据角色id删除用户信息
     */
    void deleteUserByRoleId(@Param("roelId") Integer roleId);

    /**
     * 批量插入新的用户角色数据
     * @param sysRoleUserList
     */
    void batchInsert(@Param("sysRoleUserList") List<SysRoleUser> sysRoleUserList);
}