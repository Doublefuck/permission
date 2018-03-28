package com.mmall.dao;

import com.mmall.dto.SearchLogDto;
import com.mmall.module.SysLog;
import com.mmall.module.SysLogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLogWithBLOBs record);

    int insertSelective(SysLogWithBLOBs record);

    SysLogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);

    int updateByPrimaryKey(SysLog record);

    int countBySearchDto(@Param("searchLogDto") SearchLogDto searchLogDto);

    List<SysLogWithBLOBs> getLogListBySearchDto(@Param("searchLogDto") SearchLogDto searchLogDto);
}