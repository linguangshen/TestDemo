package com.test.service.Impl;

import com.test.bean.User;
import com.test.dao.UserMapper;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findUsers() {
        return userMapper.findUsers();
    }
}
