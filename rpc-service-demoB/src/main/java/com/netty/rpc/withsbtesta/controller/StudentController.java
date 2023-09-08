package com.netty.rpc.withsbtesta.controller;

import com.netty.rpc.annotation.RpcAutowired;
import com.netty.rpc.api.testa.pojo.Student;
import com.netty.rpc.api.testa.service.IStudentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
    @RpcAutowired(version = "1.0")
    private IStudentService studentService;

    @RequestMapping("/findStudentById/{id}")
    public Student findStudentById(@PathVariable("id") Integer id) {
        return studentService.findStudentById(id);
    }
}
