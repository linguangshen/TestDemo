package com.test.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.test.domin.User;
import com.test.feign.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(value = "consumer")
//@DefaultProperties(defaultFallback = "failBack")  //全局熔断
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("{id}")
    public User getById(@PathVariable Integer id){

        //获取指定生产者的实例列表
        List<ServiceInstance> instances = discoveryClient.getInstances("user-provider");
        //获取第一个实例对象
        ServiceInstance serviceInstance = instances.get(0);
        String url ="http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/UserProvider/"+id;
        return restTemplate.getForObject(url,User.class);
    }

    @GetMapping("test/{id2}")
    public User getById2(@PathVariable Integer id2){
        String url = "http://user-provider/UserProvider/"+id2;
        return restTemplate.getForObject(url,User.class);
    }


    @GetMapping("test2/{id}")
    //@HystrixCommand(fallbackMethod = "failBack")
    @HystrixCommand
    public User getById3(@PathVariable Integer id){
        String url = "http://user-provider/UserProvider/"+id;
        return restTemplate.getForObject(url,User.class);
    }

    //全局熔断方法
    public User failBack(Integer id){
        User user = new User();
        user.setName("服务降级,默认处理！");
        return user;
    }

    @Autowired
    private UserClient userClient;

    @RequestMapping("test3/{id}")
    public User findById(@PathVariable(value = "id")Integer id){
        return userClient.getUser(id);
    }

}
