package com.netty.rpc.api.testa.service;

import com.netty.rpc.api.testa.pojo.Student;

public interface IStudentService {
    Student findStudentById(int id);

}
