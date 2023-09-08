package com.netty.rpc.withsbtesta.service;

import com.netty.rpc.annotation.NettyRpcService;
import com.netty.rpc.api.testb.service.IHelloService;

@NettyRpcService(value = IHelloService.class, version = "1.0")
public class HelloService implements IHelloService {
    @Override
    public String Hello() {
        return "hello rpc-with-springboot";
    }
}
