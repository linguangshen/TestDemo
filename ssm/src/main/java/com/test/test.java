package com.test;


import com.test.bean.Student;
import com.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class test {

    @Autowired
    HelloService helloService;

    @Autowired
    StudentService studentService;

    @RequestMapping("/hello")
    public String hello(){
        return helloService.hello();
    }

    @RequestMapping("index")
    public  String index(){
        return "test";
    }

    @GetMapping("test")
    public List<Student> getStudents(){
        return studentService.getStuedents();
    }
}
