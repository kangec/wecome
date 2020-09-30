package com.kangec.wecome.infrastructure.mapper;

import com.kangec.wecome.WeComeServerApplication;
import com.kangec.wecome.infrastructure.pojo.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author Ardien
 * @Date 9/30/2020 10:27 AM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@SpringBootTest(classes = {WeComeServerApplication.class})
public class UserMapperTest {

    @Autowired
    public UserMapper userMapper;

    @Test
    public void selectUserByUserId() {
        User user = userMapper.selectUserByUserId("1000001");
        Assert.assertNotNull(user);
    }

    @Test
    public void queryUserPassword() {
        String password = userMapper.queryUserPassword("1000001");
        Assert.assertEquals(password,"123456");
    }

    @Test
    public void queryUserContacts() {
        List<User> users = userMapper.queryUserContacts("1000001", "%ang%");
        Assert.assertNotNull(users);
        Assert.assertEquals(users.get(0).getPassword(), "123456");
    }
}