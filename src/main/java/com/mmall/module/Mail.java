package com.mmall.module;

import lombok.*;

import java.util.Set;

/**
 * Created by Administrator on 2018/3/24 0024.
 */
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    private String subject;

    private String message; // 信息

    private Set<String> receivers; // 收件人
}
