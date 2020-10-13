package com.kangec.wecome.socket.hander;

import com.alibaba.fastjson.JSON;
import com.kangec.wecome.config.ConnectionManager;
import com.kangec.wecome.infrastructure.pojo.User;
import com.kangec.wecome.service.UserService;
import com.kangec.wecome.socket.BaseHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import packet.contact.AddContactRequest;
import packet.contact.AddContactResponse;

/**
 * @Author Ardien
 * @Date 10/8/2020 10:05 AM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@ChannelHandler.Sharable
@Slf4j
public class AddContactHandler extends BaseHandler<AddContactRequest> {

    public AddContactHandler(UserService userService) {
        super(userService);
    }

    @Override
    public void channelRead(Channel ctx, AddContactRequest msg) {
        log.info("添加联系人请求： {}", JSON.toJSONString(msg));
        userService.asyncAddContact(msg);
        Channel contactChannel = ConnectionManager.get(msg.getContactId());
        User user = userService.getUserInfo(msg.getUserId());
        User contact = userService.getUserInfo(msg.getContactId());
        //对方在线则更新对方的联系人列表
        if (contactChannel != null) {
            AddContactResponse response = AddContactResponse.builder()
                    .contactId(user.getUserId())
                    .nickName(user.getNickName())
                    .avatar(user.getAvatar())
                    .build();
            contactChannel.writeAndFlush(response);
        }
        //添加到自己的联系人列表
        AddContactResponse contactResponse = AddContactResponse.builder()
                .contactId(contact.getUserId())
                .nickName(contact.getNickName())
                .avatar(contact.getAvatar())
                .build();
        log.info("添加联系人响应： {}", JSON.toJSONString(contactResponse));
        ctx.writeAndFlush(contactResponse);
    }
}
