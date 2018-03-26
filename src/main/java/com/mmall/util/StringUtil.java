package com.mmall.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/3/25 0025.
 */
public class StringUtil {

    public static List<Integer> splitToListInt(String str) {
        String[] strArr = str.split(",");
        List<String> strList = Arrays.asList(strArr);
        List<Integer> integerList = Lists.newArrayList();
        for (String string : strList) {
            Integer i = Integer.parseInt(string);
            integerList.add(i);
        }
        return integerList;
    }

    public static List<String> splitToListString(String str) {
        String[] strArr = str.split(",");
        List<String> strList = Arrays.asList(strArr);
        List<String> list = Lists.newArrayList();
        for (String string : strList) {
            list.add(string);
        }
        return list;
    }

}
