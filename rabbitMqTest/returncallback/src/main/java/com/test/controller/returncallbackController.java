package com.test.controller;

import com.test.bean.MyReturnCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class returncallbackController {
    @Autowired
    private MyReturnCallback myReturnCallback;


    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 发送消息-ReturnCallback
     * @return
     */
    @RequestMapping("/send2")
    public String send2() {
        //设置回调函数
        rabbitTemplate.setReturnCallback(myReturnCallback);
        //发送消息
        rabbitTemplate.convertAndSend("exchange_direct_callback01", "item.insert22", "hello insert");
        return "ok";
    }
}
