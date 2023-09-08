package com.netty.rpc.withsbtest.controller;

import com.netty.rpc.annotation.RpcAutowired;
import com.netty.rpc.api.testb.pojo.User;
import com.netty.rpc.api.testb.service.IUserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @RpcAutowired(version = "1.0")
    private IUserService service;

    @RequestMapping("/findUserByName/{name}")
    private User findUserByName(@PathVariable("name") String name) {
        return service.findUserByName(name);
    }

}
