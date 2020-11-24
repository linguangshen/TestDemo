package com.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.test.bean.User;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUsers")
    public String findUsers(Model model){
        List<User> users = userService.findUsers();
        model.addAttribute("users",users);
        return "user";
    }

   /* @RequestMapping("/saveUser")
    public String saveUsser(){
        User user = new User();
        userService.saveUser(user);
        return "success";
    }*/


    @RequestMapping("/getUsersTest")
    public String findUsersTest(){
        List<User> users = userService.findUsers();
//        model.addAttribute("users",users);
        Object o = JSONArray.toJSON(users);
        String s = o.toString();
        return s;
    }
}