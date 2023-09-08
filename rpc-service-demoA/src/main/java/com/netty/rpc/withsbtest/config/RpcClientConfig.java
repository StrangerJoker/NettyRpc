package com.netty.rpc.withsbtest.config;

import com.netty.rpc.client.RpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcClientConfig {

    @Value("${registry.address}")
    private String addr;

    @Bean
    public RpcClient rpcClient() {
        return new RpcClient(addr);
    }
}
