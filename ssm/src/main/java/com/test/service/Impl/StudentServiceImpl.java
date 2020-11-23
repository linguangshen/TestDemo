package com.test.service.Impl;

import com.test.bean.Student;
import com.test.mapper.StudentMapper;
import com.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Override
    public List<Student> getStuedents() {
        return studentMapper.getStudents();
    }
}
