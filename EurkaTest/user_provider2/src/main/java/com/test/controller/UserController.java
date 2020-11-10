package com.test.controller;

import com.test.domin.User;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("UserProvider")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public User getUser(@PathVariable Integer id){
        return userService.getUser(id);
    }

    @GetMapping("hello")
    public String restring(){
        return "hello";
    }
}
