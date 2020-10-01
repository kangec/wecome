package com.kangec.wecome.service;

import com.kangec.wecome.infrastructure.mapper.UserMapper;
import com.kangec.wecome.infrastructure.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private UserMapper userMapper;

    /**
     * 登陆校验
     *
     * @param userId   用户ID
     * @param password 用户密码
     * @return 登陆状态
     */
    @Override
    public boolean checkAuth(String userId, String password) {
        String userPassword = userMapper.queryUserPassword(userId);
        if (userPassword == null || password.isEmpty()) return false;
        return userPassword.equals(password);
    }

    @Override
    public User queryUserInfo(String userId) {
        return userMapper.selectUserByUserId(userId);
    }


    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
