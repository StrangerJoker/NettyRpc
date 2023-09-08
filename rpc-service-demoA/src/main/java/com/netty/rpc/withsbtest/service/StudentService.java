package com.netty.rpc.withsbtest.service;

import com.netty.rpc.annotation.NettyRpcService;
import com.netty.rpc.api.testa.pojo.Student;
import com.netty.rpc.api.testa.service.IStudentService;
import com.netty.rpc.withsbtest.config.RpcServerConfig;

import javax.annotation.Resource;

@NettyRpcService(value = IStudentService.class, version = "1.0")
public class StudentService implements IStudentService {

    @Resource
    private RpcServerConfig config;

    @Override
    public Student findStudentById(int id) {
        Student student = new Student();
        student.setId(id);
        student.setName("wade");
        student.setRank("100");
        student.setServiceName(config.getName());
        return student;
    }
}
