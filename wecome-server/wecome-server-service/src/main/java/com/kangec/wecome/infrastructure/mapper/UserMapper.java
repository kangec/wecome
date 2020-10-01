package com.kangec.wecome.infrastructure.mapper;

import com.kangec.wecome.infrastructure.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Ardien
 * @Date 9/30/2020 10:07 AM
 * @Email ardien@126.com
 * @Version 1.0
 **/
@Component
@Mapper
public interface UserMapper {

    /**
     * 查询某个用户{@link User}
     * @param userId 用户ID
     * @return 用户
     */
    User selectUserByUserId(@Param("userId") String userId);

    /**
     * 查询某个用户{@link User}的密码，用作登录
     * @param userId 用户ID
     * @return  明文密码
     */
    String queryUserPassword(@Param("userId") String userId);

    /**
     * 依据关键字查询某个用户的好友
     * @param userId 用户ID
     * @param key 关键字
     * @return 用户昵称中含有关键字的用户
     */
    List<User> queryUserContacts(@Param("userId") String userId,@Param("key") String key);

    Long insertUser(User user);
}
