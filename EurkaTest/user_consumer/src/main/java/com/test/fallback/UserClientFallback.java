package com.test.fallback;

import com.test.domin.User;
import com.test.feign.UserClient;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {
    /**
     * 服务降级实现
     * @param id
     * @return
     */
    @Override
    public User getUser(Integer id) {
        User user = new User();
        user.setName("Fallback，Feign服务降级。。。");
        return user;
    }
}