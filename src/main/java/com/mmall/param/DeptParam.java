package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 需要校验的部门参数，添加指定的注解
 * 在BeanValidator中对参数进行校验，并返回错误的字段和自定义的错误信息
 * Created by Administrator on 2018/3/17 0017.
 */
@Setter
@Getter
public class DeptParam {

    private Integer deptId;

    @NotBlank(message = "部门名称不可以为空")
    @Length(max = 15, min = 2, message = "部门名称需要在2-15个字之间")
    private String name;

    private Integer parentId = 0;

    @NotNull(message = "同级部门的展示顺序不可以为空")
    private Integer seq;

    @Length(max = 150, message = "备注长度不能超过150个字")
    private String remark;

}
