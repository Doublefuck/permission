package com.mmall.param;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 角色相关参数校验
 * Created by Administrator on 2018/3/24 0024.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleParam {

    private Integer id;

    @NotBlank(message = "角色名称不可以为空")
    @Length(min = 2, max = 20, message = "角色名称长度需要在2到20个字之间")
    private String name;

    @Max(value = 2, message = "角色类型值不合法")
    @Min(value = 1, message = "角色类型不值合法")
    private Integer type = 1;

    @Max(value = 1, message = "角色状态值不合法")
    @Min(value = 0, message = "角色状态值不合法")
    private Integer status;

    @Length(min = 0, max = 200, message = "角色备注长度需要在200个字符以内")
    private String remark;

}
