package com.netty.rpc.withsbtesta.service;

import com.netty.rpc.annotation.NettyRpcService;
import com.netty.rpc.api.testb.pojo.User;
import com.netty.rpc.api.testb.service.IUserService;
import com.netty.rpc.withsbtesta.config.RpcServerConfig;

import javax.annotation.Resource;

@NettyRpcService(value = IUserService.class, version = "1.0")
public class UserService implements IUserService {

    @Resource
    private RpcServerConfig config;

    @Override
    public User findUserByName(String name) {
        User user = new User();
        user.setName(name);
        user.setAge(22);
        user.setSchool("UESTC");
        user.setServiceName(config.getName());
        return user;
    }
}
