package com.kangec.wecome.infrastructure.mapper;

import com.kangec.wecome.WeComeServerApplication;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Ardien
 * @Date 10/2/2020 3:54 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
@SpringBootTest(classes = {WeComeServerApplication.class})
class ContactsMapperTest {
    @Autowired
    private ContactsMapper contactsMapper;

    @Test
    void queryContactsIdByUserId() {
        List<String> list = contactsMapper.queryContactsIdByUserId("1000001");
        Assert.assertNotNull(list);
        Assert.assertNotNull(list.get(0));

    }
}