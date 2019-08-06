package com.qf.util;

import java.security.SecureRandom;

/**
 * @Description:修改密码时生成随机盐值
 * @Createtime: 2019-08-05 10:28
 * 面向对象面向君，不负代码不负卿
 */
public class RandomSalt {
    public static String pwdRandom() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder(16);
        sb.append(secureRandom.nextInt(99999999)).append(secureRandom.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        return sb.toString();
    }
}
