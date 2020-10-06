package com.kangec.wecome.infrastructure.mapper;

import com.kangec.wecome.WeComeServerApplication;
import com.kangec.wecome.infrastructure.pojo.Chat;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = {WeComeServerApplication.class})
public class ChatsMapperTest {

    @Autowired
    public ChatsMapper chatsMapper;

    @Test
    void queryChats() {
        List<Chat> chatList = chatsMapper.queryChats("1000001");
        Assert.assertNotNull(chatList);
        Assert.assertNotNull(chatList.get(0));
    }
}