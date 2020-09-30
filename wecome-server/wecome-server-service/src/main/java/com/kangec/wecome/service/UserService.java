package com.kangec.wecome.service;

import com.kangec.wecome.infrastructure.pojo.Contacts;
import com.kangec.wecome.infrastructure.pojo.User;
import packet.login.dto.ChatItemDTO;
import packet.login.dto.ContactItemDTO;
import packet.login.dto.GroupItemDTO;
import packet.login.dto.MessagePaneDTO;

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
}
