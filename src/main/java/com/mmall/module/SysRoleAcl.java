package com.mmall.module;

import lombok.Builder;
import lombok.ToString;

import java.util.Date;

@Builder
@ToString
public class SysRoleAcl {
    private Integer roleAclId;

    private Integer roleId;

    private Integer aclId;

    private String operator;

    private Date operatorTime;

    private String operatorIp;

    public SysRoleAcl(Integer roleAclId, Integer roleId, Integer aclId, String operator, Date operatorTime, String operatorIp) {
        this.roleAclId = roleAclId;
        this.roleId = roleId;
        this.aclId = aclId;
        this.operator = operator;
        this.operatorTime = operatorTime;
        this.operatorIp = operatorIp;
    }

    public SysRoleAcl() {
        super();
    }

    public Integer getRoleAclId() {
        return roleAclId;
    }

    public void setRoleAclId(Integer roleAclId) {
        this.roleAclId = roleAclId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAclId() {
        return aclId;
    }

    public void setAclId(Integer aclId) {
        this.aclId = aclId;
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