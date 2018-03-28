package com.mmall.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 搜索Log日志的参数
 * Created by Administrator on 2018/3/26 0026.
 */
@Getter
@Setter
@ToString
@Builder
public class SearchLogParam {

    private Integer type;

    private String beforeSeq;

    private String afterSeq;

    private String operator;

    private String fromTime; // yyyy-MM-dd HH:mm:ss

    private String toTime;

}
