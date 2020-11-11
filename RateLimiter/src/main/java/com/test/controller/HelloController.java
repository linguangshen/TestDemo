package com.test.controller;

import com.test.service.AccessLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("test")
public class HelloController {
 
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
    @Autowired
    private AccessLimitService accessLimitService;
 
    @RequestMapping("/access")
    public String access(){
        //尝试获取令牌
        if(accessLimitService.tryAcquire()){
            //模拟业务执行500毫秒
            try {
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("请求成功");
            return "aceess success [" + sdf.format(new Date()) + "]";
        }else{
            System.out.println("请求失败");
            return "aceess limit [" + sdf.format(new Date()) + "]";
        }
    }
}