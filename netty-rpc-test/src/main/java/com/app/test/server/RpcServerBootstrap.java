package com.app.test.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RpcServerBootstrap {
    public static void main(String[] args) {
        // 从配置文件 创建一个RpcServer。
        new ClassPathXmlApplicationContext("server-spring.xml");
    }
}
