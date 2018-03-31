package com.mmall.module;

import lombok.Builder;
import lombok.ToString;

import java.util.Date;

@Builder
@ToString
public class SysLog {
    private Integer logId;

    private Integer type;

    private Integer targetId;

    private String operator;

    private Date operatorTime;

    private String operatorIp;

    private Integer status;

    public SysLog(Integer logId, Integer type, Integer targetId, String operator, Date operatorTime, String operatorIp, Integer status) {
        this.logId = logId;
        this.type = type;
        this.targetId = targetId;
        this.operator = operator;
        this.operatorTime = operatorTime;
        this.operatorIp = operatorIp;
        this.status = status;
    }

    public SysLog() {
        super();
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }

    public String getOperatorIp() {
        return operatorIp;
    }

    public void setOperatorIp(String operatorIp) {
        this.operatorIp = operatorIp == null ? null : operatorIp.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}