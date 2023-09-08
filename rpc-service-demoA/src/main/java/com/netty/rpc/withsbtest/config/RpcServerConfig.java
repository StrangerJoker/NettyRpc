package com.netty.rpc.withsbtest.config;

import com.netty.rpc.server.RpcServer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
public class RpcServerConfig {

    @Value("${spring.application.name}")
    private String name;

    @Value("${registry.address}")
    private String registryAddr;

    @Value("${server.address}")
    private String serverAddr = "localhost";

    @Value("${bind.port}")
    private String port;

    @Bean
    public RpcServer rpcServer() {
        return new RpcServer(serverAddr.concat(":").concat(port), registryAddr);
    }
}
