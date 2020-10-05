package com.kangec.wecome.service.impl;

import com.kangec.wecome.infrastructure.mapper.*;
import com.kangec.wecome.infrastructure.pojo.Chat;
import com.kangec.wecome.infrastructure.pojo.Groups;
import com.kangec.wecome.infrastructure.pojo.User;
import com.kangec.wecome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import packet.login.dto.ChatItemDTO;
import packet.login.dto.ContactItemDTO;
import packet.login.dto.GroupItemDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private ContactGroupsMapper contactGroupsMapper;
    private ChatsMapper chatsMapper;
    private GroupsMapper groupsMapper;
    private ContactsMapper contactsMapper;

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
    public List<ChatItemDTO> getChatList(String userId) {
        List<ChatItemDTO> chatList = new ArrayList<>();
        List<Chat> chats = chatsMapper.queryChats();
        if (chats == null) return null;
        chats.forEach(chat -> {
            ChatItemDTO itemDTO = buildChatItemDTO(chat);
            chatList.add(itemDTO);
        });
        return chatList;
    }

    @Override
    public List<ContactItemDTO> getContactList(String userId) {
        List<ContactItemDTO> dtoList = new ArrayList<>();
        List<String> contactIds = contactsMapper.queryContactsIdByUserId(userId);

        for (String contactId : contactIds) {
            User user = userMapper.selectUserByUserId(contactId);
            ContactItemDTO contactItemDTO = ContactItemDTO.builder()
                    .contactId(user.getUserId())
                    .contactName(user.getNickName())
                    .contactAvatar(user.getAvatar()).build();
            dtoList.add(contactItemDTO);
        }
        return dtoList;
    }

    @Override
    public List<GroupItemDTO> getGroupList(String userId) {
        List<GroupItemDTO> groupList = new ArrayList<>();
        // 拿到该用户所加入的群组ID
        List<String> groupIds = contactGroupsMapper.queryGroupIdList(userId);
        groupIds.forEach(id -> {
            GroupItemDTO itemDTO = buildGroupItemDTO(id);
            groupList.add(itemDTO);
        });

        return groupList;
    }

    /**
     * 依据 group_id 构建 {@link GroupItemDTO}
     * @param groupId group_id
     * @return GroupItemDTO
     */
    private GroupItemDTO buildGroupItemDTO(String groupId) {
        Groups groups = groupsMapper.queryGroupsById(groupId);

        return GroupItemDTO.builder()
                .groupId(groups.getGroupId())
                .groupName(groups.getGroupName())
                .groupAvatar(groups.getAvatar())
                .build();
    }

    /**
     *
     * @param chat
     * @return
     */
    private ChatItemDTO buildChatItemDTO(Chat chat) {
        ChatItemDTO chatItemDTO = null;
        String id = chat.getChatId();
        // 好友
        if (chat.getChatType()==0) {
            User user = userMapper.selectUserByUserId(id);
            chatItemDTO = ChatItemDTO.builder()
                    .chatType(0)
                    .chatId(user.getUserId())
                    .avatar(user.getAvatar())
                    .nick(user.getNickName())
                    .build();
        } else
        // 群组
        if (chat.getChatType() == 1) {
            Groups group = groupsMapper.queryGroupsById(id);
            if (group == null) return null;
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
    public void setContactsMapper(ContactsMapper contactsMapper) {
        this.contactsMapper = contactsMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
