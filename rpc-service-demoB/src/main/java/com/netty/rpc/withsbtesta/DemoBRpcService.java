package com.netty.rpc.withsbtesta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoBRpcService {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoBRpcService.class, args);
    }
}
