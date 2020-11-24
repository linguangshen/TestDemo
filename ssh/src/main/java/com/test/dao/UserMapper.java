package com.test.dao;

import com.test.bean.User;

import java.util.List;

public interface UserMapper {
    public List<User> findUsers();
}
