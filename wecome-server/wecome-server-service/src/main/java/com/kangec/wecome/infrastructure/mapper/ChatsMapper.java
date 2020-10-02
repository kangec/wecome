package com.kangec.wecome.infrastructure.mapper;

import com.kangec.wecome.infrastructure.pojo.Chat;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Ardien
 * @Date 9/30/2020 9:55 AM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Component
@Mapper
public interface ChatsMapper {
    List<Chat> queryChats();
}
