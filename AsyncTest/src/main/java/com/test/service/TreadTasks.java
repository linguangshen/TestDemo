package com.test.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

//线程任务类
@Component
public class TreadTasks {
    //@Async加在线程任务的方法上（需要异步执行的任务）
    @Async
    public void startTrendTask(){
        System.out.println("this is my async task");
    }
}