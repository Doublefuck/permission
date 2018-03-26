package com.mmall.dao;

import com.mmall.module.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    SysUser findByKeyword(@Param("keyword") String keyword);

    int countByEmail(@Param("email") String email, @Param("userId") Integer userId);

    int countByTelephone(@Param("telephone") String telephone, @Param("userId") Integer userId);

    List<SysUser> getByUserIdList(@Param("userIds") List<Integer> userIds);

    List<SysUser> getAll();

    int countByDeptId(@Param("deptId") int deptId);
}