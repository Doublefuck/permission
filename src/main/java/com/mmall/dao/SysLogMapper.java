package com.mmall.dao;

import com.mmall.dto.SearchLogDto;
import com.mmall.module.SysLog;
import com.mmall.module.SysLogWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysLogMapper {
    int deleteByPrimaryKey(Integer logId);

    int insert(SysLogWithBLOBs record);

    int insertSelective(SysLogWithBLOBs record);

    SysLogWithBLOBs selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(SysLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);

    int updateByPrimaryKey(SysLog record);

    /**
     * 查询日志的数目
     * @param searchLogDto
     * @return
     */
    int countBySearchDto(@Param("searchLogDto") SearchLogDto searchLogDto);

    /**
     * 根据搜索条件查询日志信息
     * @param searchLogDto
     * @return
     */
    List<SysLogWithBLOBs> getLogListBySearchDto(@Param("searchLogDto") SearchLogDto searchLogDto);
}