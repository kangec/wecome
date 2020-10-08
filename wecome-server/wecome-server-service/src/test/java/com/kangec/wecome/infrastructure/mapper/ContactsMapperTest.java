package com.kangec.wecome.infrastructure.mapper;

import com.kangec.wecome.WeComeServerApplication;
import com.kangec.wecome.infrastructure.pojo.Contacts;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
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
    public ContactsMapper contactsMapper;

    @Test
    public void queryContactsIdByUserId() {
        List<String> list = contactsMapper.queryContactsIdByUserId("1000001");
        Assert.assertNotNull(list);
        Assert.assertNotNull(list.get(0));

    }

    @Test
    public void insertContact() {
        Date now = new Date();
        Contacts contact = Contacts.builder()
                .userId("1000003")
                .contactId("1000004")
                .createTime(now)
                .updateTime(now)
                .build();
        contactsMapper.insertContact(contact);
    }
}