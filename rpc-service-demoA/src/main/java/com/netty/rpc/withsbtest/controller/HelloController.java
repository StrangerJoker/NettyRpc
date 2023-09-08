package com.netty.rpc.withsbtest.controller;

import com.netty.rpc.annotation.RpcAutowired;
import com.netty.rpc.api.testb.service.IHelloService;
import com.netty.rpc.client.RpcClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Resource
    private RpcClient rpcClient;

    @RequestMapping("/hello")
    private String hello() {
        IHelloService service = rpcClient.createService(IHelloService.class, "1.0");
        return service.Hello();
    }

    @RpcAutowired(version = "1.0")
    private IHelloService service;

    @RequestMapping("/hello-auto")
    private String helloAuto() {
        return service.Hello();
    }
}
