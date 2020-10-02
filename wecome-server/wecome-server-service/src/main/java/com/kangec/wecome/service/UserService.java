package com.kangec.wecome.service;

import com.kangec.wecome.infrastructure.pojo.Chat;
import com.kangec.wecome.infrastructure.pojo.User;
import packet.login.dto.ChatItemDTO;
import packet.login.dto.ContactItemDTO;


import java.util.List;

public interface UserService {
    /**
     * 登陆校验
     *
     * @param userId    用户ID
     * @param password  用户密码
     * @return 登陆状态
     */
    boolean checkAuth(String userId, String password);

    /**
     * 查找用户信息
     *
     * @param userId userId
     * @return {@link User}
     */
    User getUserInfo(String userId);


    /**
     * 查找 userId 下的群组列表Id
     * @param userId userId
     * @return GroupIdList
     */
    List<String> getGroupIds(String userId);

    /**
     * 查询 userId 下的对话框列表
     * @param userId userId
     * @return ChatItemDTO列表
     */
    List<Chat> getChats(String userId);

    List<ContactItemDTO> getContactList(String userId);
}
