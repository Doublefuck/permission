package com.mmall.dto;

import lombok.*;

import java.util.Date;

/**
 * 映射到数据库中日志表sys_log的参数
 * Created by Administrator on 2018/3/26 0026.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchLogDto {

    private Integer type;

    private String beforeSeq;

    private String afterSeq;

    private String operator;

    private Date fromTime; // yyyy-MM-dd HH:mm:ss

    private Date toTime;

}
