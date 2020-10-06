package com.kangec.wecome.infrastructure.mapper;

import com.kangec.wecome.WeComeServerApplication;
import com.kangec.wecome.infrastructure.pojo.Message;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * @Author Ardien
 * @Date 10/5/2020 1:27 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@SpringBootTest(classes = {WeComeServerApplication.class})
class MessageMapperTest {

    @Autowired
    public MessageMapper messageMapper;
    @Test
    void queryMessageByUserId() {
        List<Message> list = messageMapper.queryMessageByUserId("1000001", "20001");
        Assert.assertNotNull(list);
        Assert.assertNotNull(list.get(0));
    }

    @Test
    void insertMessageRecord() {
        Message message = Message.builder()
                .userId("1000001")
                .contactId("1000002")
                .chatType(0)
                .msgTime(new Date())
                .body("测试测试测试测试，测试测试测试测试。")
                .build();
        messageMapper.insertMessageRecord(message);
    }
}