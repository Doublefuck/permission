package com.mmall.dao;

import com.mmall.module.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    /**
     * 获取角色id对应的角色
     * @param roleIdList
     * @return
     */
    List<SysRole> getByIdList(@Param("roleIdList") List<Integer> roleIdList);

    /**
     * 查询所有状态角色的总数量
     * @return
     */
    int countRole();

    /**
     * 获取所有角色
     * @return
     */
    List<SysRole> getAll();

    /**
     * 检查是否存在相同名称的角色
     * @param name
     * @return
     */
    int countRoleByName(@Param("name") String name);
}