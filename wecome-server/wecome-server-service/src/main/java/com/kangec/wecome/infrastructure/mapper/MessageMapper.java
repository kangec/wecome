package com.kangec.wecome.infrastructure.mapper;

import com.kangec.wecome.infrastructure.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Ardien
 * @Date 10/5/2020 1:00 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Mapper
@Component
public interface MessageMapper {
    List<Message> queryMessageByUserId(@Param("userId") String userId, @Param("chatId") String chatId);
}
