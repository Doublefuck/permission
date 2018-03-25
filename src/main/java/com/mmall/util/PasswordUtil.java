package com.mmall.util;

import java.util.Date;
import java.util.Random;

/**
 * 密码生成工具类
 * Created by Administrator on 2018/3/22 0022.
 */
public class PasswordUtil {

    public static final String[] word = {
            "a","b","c","d","e","f","g","h","j","k",
            "m","n","p","q","r","s","t","u","v","w",
            "x","y","z","A","B","C","D","E","F","G",
            "H","J","K","M","N","P","Q","R","S","T",
            "U","V","W","X","Y","Z"
    };
    public static final String[] num = {
            "2","3","4","5","6","7","8","9"
    };

    /**
     * 随机生成数字字母相间的密码
     * @return
     */
    public static String randomPassword() {
        StringBuffer sb = new StringBuffer();
        // 以当前时间作为随机数
        Random random = new Random(new Date().getTime());
        boolean flag = false;
        // 默认密码长度8到10
        int length = random.nextInt(3) + 8;
        for (int i = 0 ; i < length ; i++) {
            if (flag) {
                sb.append(num[random.nextInt(num.length)]);
            } else {
                sb.append(word[random.nextInt(word.length)]);
            }
            flag = true;
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        System.out.println(PasswordUtil.randomPassword());
    }
}
