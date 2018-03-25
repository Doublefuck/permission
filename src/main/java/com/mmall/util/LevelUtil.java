package com.mmall.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 部门层级管理工具类
 * Created by Administrator on 2018/3/22 0022.
 */
public class LevelUtil {

    public static final String SEPARATOR = "."; // 部门层级分隔符

    public static final String ROOT = "0"; // 最顶层级部门

    /**
     * 计算部门层级
     * @param parentLevel
     * @param parentId
     * @return
     */
    public static String calculateLevel(String parentLevel, Integer parentId) {
        // 组装当前部门层级，类似于0.1.2.2...
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }

}
