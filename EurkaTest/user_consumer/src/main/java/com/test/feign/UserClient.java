package com.test.feign;

import com.test.domin.User;
import com.test.fallback.UserClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "user-provider",fallback = UserClientFallback.class)  //声明Feign的客户端，value:填写生产者的名称
public interface UserClient {

    //注意：此处的请求地址一定要和生产者请求的地址一样
    @GetMapping("UserProvider/{id}")
    public User getUser(@PathVariable Integer id);
}