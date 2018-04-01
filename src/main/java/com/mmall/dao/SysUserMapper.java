package com.mmall.dao;

import com.mmall.module.SysUser;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 检查当前部门下是否存在用户
     * @param deptId
     * @return
     */
    int countByDeptId(@Param("deptId") Integer deptId);

    /**
     * 根据关键字检索用户是否存在，关键字是邮箱或者手机
     * @param keyword
     * @return
     */
    SysUser loginByKeyword(@Param("keyword") String keyword);

    /**
     * 获取当前所有状态的用户总数量
     * @return
     */
    int countUser();

    /**
     * 获取所有用户信息
     * @return
     */
    List<SysUser> getAll();

    /**
     * 校验用户名是否存在
     * @param username
     * @return
     */
    int countByUsername(@Param("username") String username, @Param("userId") Integer userId);

    /**
     * 校验用户邮箱是否存在
     * @param email
     * @param userId
     * @return
     */
    int countByEmail(@Param("email") String email, @Param("userId") Integer userId);

    /**
     * 校验用户手机号是否存在
     * @param phone
     * @param userId
     * @return
     */
    int countByTelephone(@Param("phone") String phone, @Param("userId") Integer userId);

    /**
     * 根据用户id获取对应的用户信息
     * @param userIdList
     * @return
     */
    List<SysUser> getByUserIdList(@Param("userIdList") List<Integer> userIdList);

    /**
     * 根据关键字查询
     * @param keyword
     * @return
     */
    int findByKeyword(@Param("keyword") String keyword);

    /**
     * 查询用户所属的部门是否存在
     * @param deptId
     * @return
     */
    int checkDeptExist(@Param("deptId") Integer deptId);

}