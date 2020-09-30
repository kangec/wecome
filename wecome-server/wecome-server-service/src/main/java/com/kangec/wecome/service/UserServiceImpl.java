package com.kangec.wecome.service;

import com.kangec.wecome.infrastructure.mapper.UserMapper;
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
        return userPassword.equals(password);
    }


    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
