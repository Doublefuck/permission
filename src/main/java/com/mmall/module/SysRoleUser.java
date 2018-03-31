package com.mmall.module;

import lombok.Builder;
import lombok.ToString;

import java.util.Date;

@Builder
@ToString
public class SysRoleUser {
    private Integer roleUserId;

    private Integer roleId;

    private Integer userId;

    private String operator;

    private Date operatorTime;

    private String operatorIp;

    public SysRoleUser(Integer roleUserId, Integer roleId, Integer userId, String operator, Date operatorTime, String operatorIp) {
        this.roleUserId = roleUserId;
        this.roleId = roleId;
        this.userId = userId;
        this.operator = operator;
        this.operatorTime = operatorTime;
        this.operatorIp = operatorIp;
    }

    public SysRoleUser() {
        super();
    }

    public Integer getRoleUserId() {
        return roleUserId;
    }

    public void setRoleUserId(Integer roleUserId) {
        this.roleUserId = roleUserId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
}