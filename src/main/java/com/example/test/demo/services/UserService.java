package com.example.test.demo.services;

import com.example.test.demo.mapper.UserMapper;
import com.example.test.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User myUser) {
        User dbUser = userMapper.findByAccountId(myUser.getAccountId());
        if (dbUser == null) {
            myUser.setGmtCreate(System.currentTimeMillis());
            myUser.setGmtModified(myUser.getGmtModified());
            userMapper.insert(myUser);
        }else {
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatar(myUser.getAvatar());
            dbUser.setName(myUser.getName());
            dbUser.setToken(myUser.getToken());
            userMapper.update(dbUser);
        }
    }
}
