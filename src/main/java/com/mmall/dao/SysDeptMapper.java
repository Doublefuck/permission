package com.mmall.dao;

import com.mmall.module.SysDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDeptMapper {
    int deleteByPrimaryKey(Integer deptId);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    int countDept();

    List<SysDept> getAllDept();

    int countByNameAndParentId(@Param("parentId") Integer parentId,@Param("deptName") String deptName,@Param("deptId") Integer deptId);

    List<SysDept> getChildDeptListByLevel(@Param("level") String level);

    int batchUpdateLevel(@Param("sysDeptList") List<SysDept> sysDeptList);

    int countByParentId(@Param("parentId") Integer parentId);

}