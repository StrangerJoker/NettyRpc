package com.netty.rpc.api.testb.service;

import com.netty.rpc.api.testb.pojo.User;

public interface IUserService {
    User findUserByName(String name);
}
