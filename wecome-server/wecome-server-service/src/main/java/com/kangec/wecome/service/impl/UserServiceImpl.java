package com.kangec.wecome.service.impl;

import com.kangec.wecome.infrastructure.mapper.ChatsMapper;
import com.kangec.wecome.infrastructure.mapper.ContactGroupsMapper;
import com.kangec.wecome.infrastructure.mapper.GroupsMapper;
import com.kangec.wecome.infrastructure.mapper.UserMapper;
import com.kangec.wecome.infrastructure.pojo.Chat;
import com.kangec.wecome.infrastructure.pojo.Groups;
import com.kangec.wecome.infrastructure.pojo.User;
import com.kangec.wecome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import packet.login.dto.ChatItemDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private ContactGroupsMapper contactGroupsMapper;
    private ChatsMapper chatsMapper;
    private GroupsMapper groupsMapper;

    /**
     * 登陆校验
     *
     * @param userId   用户ID
     * @param password 用户密码
     * @return 登陆状态
     */
    @Override
    public boolean checkAuth(String userId, String password) {
        String userPassword = userMapper.queryUserPassword(userId);
        if (userPassword == null || password.isEmpty()) return false;
        return userPassword.equals(password);
    }

    @Override
    public User getUserInfo(String userId) {
        return userMapper.selectUserByUserId(userId);
    }

    @Override
    public List<String> getGroupIds(String userId) {
        if (userId == null || userId.isEmpty()) return null;
        return contactGroupsMapper.queryGroupIdList(userId);
    }

    @Override
    public List<ChatItemDTO> getChatListDTD(String userId) {
        List<Chat> chatList = chatsMapper.queryChats();
        List<ChatItemDTO> chatItemDTOList = new ArrayList<>();
        // 组装ChatItemDTO
        for (Chat chat : chatList) {
            ChatItemDTO chatItemDTO = buildChatItemDTO(chat);
            chatItemDTOList.add(chatItemDTO);
        }
        return chatItemDTOList;
    }

    private ChatItemDTO buildChatItemDTO(Chat chat) {
        ChatItemDTO chatItemDTO = null;
        String id = chat.getUserId();
        // 好友
        if (chat.getChatType() == 0) {
            User user = userMapper.selectUserByUserId(id);
            chatItemDTO = ChatItemDTO.builder()
                    .chatType(0)
                    .chatId(user.getUserId())
                    .avatar(user.getAvatar())
                    .nick(user.getNickName())
                    .build();
        }
        // 群组
        if (chat.getChatType() == 1) {
            Groups group = groupsMapper.queryGroupsById(id);
            chatItemDTO = ChatItemDTO.builder()
                    .chatType(1)
                    .chatId(group.getGroupId())
                    .nick(group.getGroupName())
                    .avatar(group.getAvatar())
                    .build();
        }
        return chatItemDTO;
    }




    @Autowired
    public void setGroupsMapper(GroupsMapper groupsMapper) {
        this.groupsMapper = groupsMapper;
    }

    @Autowired
    public void setChatsMapper(ChatsMapper chatsMapper) {
        this.chatsMapper = chatsMapper;
    }

    @Autowired
    public void setContactGroupsMapper(ContactGroupsMapper contactGroupsMapper) {
        this.contactGroupsMapper = contactGroupsMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
