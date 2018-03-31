package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 需要校验用户的相关参数
 * Created by Administrator on 2018/3/22 0022.
 */
@Setter
@Getter
public class UserParam {

    private Integer UserId;

    @NotBlank(message = "用户名不可以为空")
    @Length(min = 0 ,max = 20, message = "用户名长度需要在20个字以内")
    private String username;

    @NotBlank(message = "电话不可以为空")
    @Length(min = 1, max = 11, message = "电话长度需要在11个字以内")
    private String telephone;

    @NotBlank(message = "邮箱不可以为空")
    @Length(min = 5, max = 50, message = "邮箱长度需要在5到50个字符以内")
    private String email;

    @NotNull(message = "必须提供用户所在的部门")
    private Integer deptId;

    @NotNull(message = "必须指定用户的状态")
    @Min(value = 0, message = "用户状态不合法")
    @Max(value = 2, message = "用户状态不合法")
    private Integer status;

    @Length(min = 0, max = 200, message = "备注长度需要在200个字以内")
    private String remark = "";
}
