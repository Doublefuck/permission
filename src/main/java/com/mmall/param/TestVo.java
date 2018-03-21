package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 测试类
 * Created by Administrator on 2018/3/21 0021.
 */
@Setter
@Getter
public class TestVo {

    @NotBlank
    private String msg;

    @NotNull
    private Integer id;
}
