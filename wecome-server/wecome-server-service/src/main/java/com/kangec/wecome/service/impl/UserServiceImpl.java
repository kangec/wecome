package com.kangec.wecome.service.impl;

import com.kangec.wecome.infrastructure.mapper.*;
import com.kangec.wecome.infrastructure.pojo.*;
import com.kangec.wecome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import packet.chat.ChatDialogRequest;
import packet.chat.dto.ChatItemDTO;
import packet.contact.AddContactRequest;
import packet.contact.dto.ContactItemDTO;
import packet.contact.dto.GroupItemDTO;
import packet.contact.dto.SearchResultDTO;
import packet.message.MessageRequest;
import packet.message.dto.MessagePaneDTO;
import utils.StatusCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static utils.StatusCode.ChatType.GROUP;
import static utils.StatusCode.ChatType.PERSONAL;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private ContactGroupsMapper contactGroupsMapper;
    private ChatsMapper chatsMapper;
    private GroupsMapper groupsMapper;
    private ContactsMapper contactsMapper;
    private MessageMapper messageMapper;
    private ThreadPoolTaskExecutor executor;

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
        List<Chat> chats = chatsMapper.queryChats(userId);
        if (chats == null) return null;
        chats.forEach(chat -> {
            ChatItemDTO itemDTO = buildChatItemDTO(chat, userId);
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

    @Override
    public void asyncAddMessageRecord(MessageRequest msg) {
        executor.execute(() -> {
            Message message = Message.builder()
                    .userId(msg.getUserId())
                    .contactId(msg.getContactId())
                    .chatType(msg.getMsgType())
                    .body(msg.getMsgBody())
                    .msgTime(msg.getMsgDate())
                    .build();
            messageMapper.insertMessageRecord(message);
        });
    }

    @Override
    public void asyncResolveChatRecord(ChatDialogRequest msg) {
        StatusCode.Action action = msg.getAction();
        switch (action) {
            case ADD:
                addChatDialog(msg);
                break;
            case DELETE:
                deleteChatDialog(msg);
                break;
            default:
                throw new RuntimeException("Action Not Defend");
        }
    }

    @Override
    public Long getChatList(String userId, String contactId) {
        return chatsMapper.queryChatsByUserIdWithChatId(userId, contactId);
    }

    @Override
    public List<SearchResultDTO> searchContacts(String userId, String key) {
        List<SearchResultDTO> res = new ArrayList<>();
        key = "%" + key + "%";
        List<User> usersAll = userMapper.queryUserContacts(userId, key);
        List<String> addedIds = contactsMapper.queryContactsIdByUserId(userId);
        usersAll.forEach(user -> {
            int status = 0;
            if (addedIds.contains(user.getUserId())) {
                status = 2;
            }
            SearchResultDTO dto = SearchResultDTO.builder().contactId(user.getUserId())
                    .contactAvatar(user.getAvatar())
                    .contactName(user.getNickName())
                    .status(status)
                    .build();
            res.add(dto);
        });
        return res;
    }

    @Override
    public void asyncAddContact(AddContactRequest msg) {
        executor.execute(() -> {
            Date now = new Date();
            String userId = msg.getUserId();
            String contactId = msg.getContactId();
            Contacts contactSelf = Contacts.builder()
                                        .userId(userId)
                                        .contactId(contactId)
                                        .createTime(now)
                                        .updateTime(now)
                                        .build();

            Contacts contact = Contacts.builder()
                                    .userId(contactId)
                                    .contactId(userId)
                                    .createTime(now)
                                    .updateTime(now)
                                    .build();

            // 向自己的通讯录插入对方的联系人信息
            contactsMapper.insertContact(contactSelf);

            // 向对方的通讯录插入自己的联系人信息
            contactsMapper.insertContact(contact);
            now = null;
        });
    }

    @Override
    public User doRegister(User user) {
        Date now = new Date();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        user.setAvatar("16");
        String userId = String.valueOf(System.currentTimeMillis() % 100000000);
        user.setUserId(userId);
        Long aLong = userMapper.insertUser(user);
        return aLong > 0 ? user : null;
    }


    private void deleteChatDialog(ChatDialogRequest msg) {
        chatsMapper.delete(msg.getUserId(), msg.getContactId());
    }

    private void addChatDialog(ChatDialogRequest msg) {
        executor.execute(() -> {
            // 如果对方没有与你的对话框，则要将对话框数据传给他
            if (chatsMapper.queryChatsByUserIdWithChatId(msg.getContactId(), msg.getUserId()) == null) {
                Date now = new Date();
                Chat contact = Chat.builder()
                        .userId(msg.getContactId())
                        .chatId(msg.getUserId())
                        .chatType(msg.getChatType())
                        .createTime(now)
                        .updateTime(now)
                        .build();
                chatsMapper.insert(contact);
            }
            // 如果己方没有存入
            if (chatsMapper.queryChatsByUserIdWithChatId(msg.getUserId(), msg.getContactId()) == null) {
                Date now = new Date();
                Chat chat = Chat.builder()
                        .userId(msg.getUserId())
                        .chatId(msg.getContactId())
                        .chatType(msg.getChatType())
                        .createTime(now)
                        .updateTime(now)
                        .build();
                chatsMapper.insert(chat);
            }
        });
    }

    /**
     * 依据 group_id 构建 {@link GroupItemDTO}
     *
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
     * @param chat
     * @return
     */
    private ChatItemDTO buildChatItemDTO(Chat chat, String userId) {
        ChatItemDTO chatItemDTO = null;
        List<MessagePaneDTO> messagePaneList = null;
        String chatId = chat.getChatId();
        List<Message> messages = messageMapper.queryMessageByUserId(userId, chatId);
        Message lastMsg = null;
        if (messages == null) {
            lastMsg = Message.builder().body("").msgTime(new Date()).build();
        } else {
            lastMsg = messages.get(0);
        }

        // 好友
        if (chat.getChatType() == PERSONAL.getValue()) {
            User user = userMapper.selectUserByUserId(chatId);
            messagePaneList = buildUserMessagePaneList(messages, userId, chatId);
            chatItemDTO = ChatItemDTO.builder()
                    .chatType(0)
                    .chatId(user.getUserId())
                    .avatar(user.getAvatar())
                    .nick(user.getNickName())
                    .date(lastMsg.getMsgTime())
                    .msg(lastMsg.getBody())
                    .messagePaneList(messagePaneList)
                    .build();
        } else
            // 群组
            if (chat.getChatType() == StatusCode.ChatType.GROUP.getValue()) {
                Groups group = groupsMapper.queryGroupsById(chatId);
                if (group == null) return null;
                messagePaneList = buildGroupMessagePaneList(messages, userId,group.getGroupId(), chatId);
                chatItemDTO = ChatItemDTO.builder()
                        .chatType(1)
                        .chatId(group.getGroupId())
                        .nick(group.getGroupName())
                        .date(lastMsg.getMsgTime())
                        .msg(lastMsg.getBody())
                        .messagePaneList(messagePaneList)
                        .avatar(group.getAvatar())
                        .build();
            }
        return chatItemDTO;
    }

    /**
     * 构建群组 groupId 内的历史消息列表
     * @param messages  历史消息
     * @param groupId   群组Id
     * @param chatId    对话框Id
     * @return          {@link MessagePaneDTO} 列表
     */
    private List<MessagePaneDTO> buildGroupMessagePaneList(List<Message> messages, String userId, String groupId, String chatId) {
        if (messages == null) return null;
        List<MessagePaneDTO> list = new ArrayList<>();
        messages.forEach(message -> {
            if (message.getChatType() == GROUP.getValue()) {
                boolean msgType = userId.equals(message.getUserId());
                // TODO 信息构建
                MessagePaneDTO item = MessagePaneDTO.builder()
                        .msgPaneId(chatId)
                        .userId(msgType ? userId : message.getContactId())
                        .msgFlag(msgType ? 0 : 1)
                        .msgBody(message.getBody())
                        .msgDate(message.getMsgTime())
                        .msgType(message.getChatType())
                        .build();
                list.add(item);
            }
        });
        return list;
    }

    /**
     * 聊天记录数据传输列表
     *
     * @param messages 消息记录
     * @param chatId 对话框Id
     * @param userId 用户Id
     * @return {@link MessagePaneDTO} 列表
     */
    private List<MessagePaneDTO> buildUserMessagePaneList(List<Message> messages, String userId, String chatId) {
        if (messages == null) return null;
        List<MessagePaneDTO> list = new ArrayList<>();
        messages.forEach(message -> {
            if (message.getChatType() == PERSONAL.getValue()) {
                boolean msgType = userId.equals(message.getUserId());
                MessagePaneDTO dto = MessagePaneDTO.builder()
                        .msgPaneId(chatId)
                        .userId(msgType ? userId : message.getContactId())
                        .msgFlag(msgType ? 0 : 1)
                        .msgBody(message.getBody())
                        .msgDate(message.getMsgTime())
                        .msgType(message.getChatType())
                        .build();
                list.add(dto);
            }
        });
        return list;
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

    @Autowired
    public void setMessageMapper(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    @Autowired
    public void setExecutor(ThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }
}
