package com.kangec.wecome.infrastructure.mapper;

import com.kangec.wecome.WeComeServerApplication;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author Ardien
 * @Date 10/2/2020 12:57 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@SpringBootTest(classes = {WeComeServerApplication.class})
public class ContactGroupsMapperTest {

    @Autowired
    public ContactGroupsMapper contactGroupsMapper;

    @Test
    public void queryGroupIdList() {
        List<String> groupIdList = contactGroupsMapper.queryGroupIdList("1000001");
        Assert.assertNotNull(groupIdList);
        Assert.assertEquals(groupIdList.get(0), "20001");
    }
}