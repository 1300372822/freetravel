package com.qf.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 2019/6/25
 * Administrator
 * springTest
 * 面向对象面向君  不负代码不负卿
 */
public class Test1 {
    public static void main(String[] args) {
        Md5Hash md5Hash=new Md5Hash("0","hello",1);
        System.out.println(md5Hash.toString());
    }
}
