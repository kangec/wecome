package com.kangec.wecome.infrastructure.mapper;

import com.kangec.wecome.WeComeServerApplication;
import com.kangec.wecome.infrastructure.pojo.Groups;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Ardien
 * @Date 10/2/2020 2:55 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@SpringBootTest(classes = {WeComeServerApplication.class})
class GroupsMapperTest {

    @Autowired
    public GroupsMapper groupsMapper;
    @Test
    void queryGroupsById() {
        Groups groups = groupsMapper.queryGroupsById("20002");
        Assert.assertNotNull(groups);
    }
}