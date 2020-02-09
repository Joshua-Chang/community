package com.example.test.demo.services;

import com.example.test.demo.mapper.UserMapper;
import com.example.test.demo.model.User;
import com.example.test.demo.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User myUser) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(myUser.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
//        User dbUser = userMapper.findByAccountId(myUser.getAccountId());
        if (users.size()==0) {
            myUser.setGmtCreate(System.currentTimeMillis());
            myUser.setGmtModified(myUser.getGmtModified());
            userMapper.insert(myUser);
        }else {
            User dbUser = users.get(0);
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatar(myUser.getAvatar());
            dbUser.setName(myUser.getName());
            dbUser.setToken(myUser.getToken());
            UserExample example = new UserExample();
            example.createCriteria().andAccountIdEqualTo(myUser.getAccountId());
            userMapper.updateByExampleSelective(dbUser, example);//更新选中的
//            userMapper.update(dbUser);
        }
    }
}
