package com.kangec.wecome.infrastructure.mapper;


import com.kangec.wecome.infrastructure.pojo.Contacts;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ContactsMapper {
    List<String> queryContactsIdByUserId(String userId);

    void insertContact(Contacts contact);
}
