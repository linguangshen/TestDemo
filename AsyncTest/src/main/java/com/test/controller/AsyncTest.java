package com.test.controller;


import com.test.service.TreadTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class AsyncTest {

    @Autowired
    private TreadTasks treadTasks;

    @GetMapping("test1")
    public void test1(){
        treadTasks.startTrendTask();
    }

}
