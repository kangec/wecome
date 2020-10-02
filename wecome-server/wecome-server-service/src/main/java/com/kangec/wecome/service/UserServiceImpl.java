package com.kangec.wecome.service;

import com.kangec.wecome.infrastructure.mapper.ContactGroupsMapper;
import com.kangec.wecome.infrastructure.mapper.UserMapper;
import com.kangec.wecome.infrastructure.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private UserMapper userMapper;
    private ContactGroupsMapper contactGroupsMapper;

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
    public User getUserInfo(String userId) {
        return userMapper.selectUserByUserId(userId);
    }

    @Override
    public List<String> getGroupIds(String userId) {
        if (userId == null || userId.isEmpty()) return null;
        return contactGroupsMapper.queryGroupIdList(userId);
    }

    @Autowired
    public void setContactGroupsMapper(ContactGroupsMapper contactGroupsMapper) {
        this.contactGroupsMapper = contactGroupsMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
