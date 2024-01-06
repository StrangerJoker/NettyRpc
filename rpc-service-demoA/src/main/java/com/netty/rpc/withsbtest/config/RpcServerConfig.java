package com.netty.rpc.withsbtest.config;

import com.netty.rpc.server.RpcServer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.Inet4Address;
import java.net.UnknownHostException;

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
    public RpcServer rpcServer() throws UnknownHostException {
        if (serverAddr.equalsIgnoreCase("localhost")) {
            serverAddr = Inet4Address.getLocalHost().getHostAddress();
        }
        return new RpcServer(serverAddr.concat(":").concat(port), registryAddr);
    }
}
