package com.app.test;

import org.junit.Assert;
import org.junit.Test;

public class TestA {
    @Test
    public void test() {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        String str2 = str1.intern();
        String str3 = new StringBuilder("ja").append("va").toString();
        String str4 = str3.intern();
        String str5 = new StringBuilder("c").append("++").toString();
        String str6 = str5.intern();
        System.out.println(str1 == str2);
        System.out.println(str3 == str4);
        System.out.println(str5 == str6);
    }
}
