package com.kangec.wecome.service;

import com.kangec.wecome.infrastructure.pojo.User;
import packet.chat.ChatDialogRequest;
import packet.chat.dto.ChatItemDTO;
import packet.contact.AddContactRequest;
import packet.contact.dto.ContactItemDTO;
import packet.contact.dto.GroupItemDTO;
import packet.contact.dto.SearchResultDTO;
import packet.message.MessageRequest;


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
    List<ChatItemDTO> getChatList(String userId);

    /**
     * 获取联系人列表
     * @param userId userId
     * @return ContactItemDTO列表数据
     */
    List<ContactItemDTO> getContactList(String userId);

    /**
     * 获取群组列表
     * @param userId userId
     * @return 群组列表数据
     */
    List<GroupItemDTO> getGroupList(String userId);

    /**
     * 异步添加消息记录
     * @param msg 消息记录
     */
    void asyncAddMessageRecord(MessageRequest msg);

    /**
     * 异步添加对话框记录
     * @param msg 对话框记录
     */
    void asyncResolveChatRecord(ChatDialogRequest msg);

    /**
     * 查询两个用户之间的对话框
     * @param userId userId
     * @param contactId contactId
     * @return ChatID
     */
    Long getChatList(String userId, String contactId);

    /**
     * 搜索联系人
     * @param userId userId
     * @param key 关键字
     * @return SearchResultDTO列表
     */
    List<SearchResultDTO> searchContacts(String userId, String key);

    /**
     * 异步添加联系人
     * @param msg 添加联系人请求
     */
    void asyncAddContact(AddContactRequest msg);

    public User doRegister(User user);
}
