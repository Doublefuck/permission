package com.mmall.module;

import lombok.Builder;
import lombok.ToString;

import java.util.Date;

@Builder
@ToString
public class SysRole {
    private Integer roleId;

    private String name;

    private Integer type;

    private Integer status;

    private String remark;

    private String operator;

    private Date operatorTime;

    private String operatorIp;

    public SysRole(Integer roleId, String name, Integer type, Integer status, String remark, String operator, Date operatorTime, String operatorIp) {
        this.roleId = roleId;
        this.name = name;
        this.type = type;
        this.status = status;
        this.remark = remark;
        this.operator = operator;
        this.operatorTime = operatorTime;
        this.operatorIp = operatorIp;
    }

    public SysRole() {
        super();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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