package com.kangec.wecome.socket.hander;

import com.alibaba.fastjson.JSON;
import com.kangec.wecome.config.ChannelBeansCache;
import com.kangec.wecome.infrastructure.pojo.User;
import com.kangec.wecome.service.UserService;
import com.kangec.wecome.socket.BaseHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import packet.chat.ChatDialogResponse;
import packet.message.MessageRequest;
import packet.message.MessageResponse;

/**
 * @Author Ardien
 * @Date 10/5/2020 8:48 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@ChannelHandler.Sharable
@Slf4j
public class MessageHandler extends BaseHandler<MessageRequest> {
    public MessageHandler(UserService userService) {
        super(userService);
    }


    @Override
    public void channelRead(Channel ctx, MessageRequest msg) {
        log.info("接受客户端消息: {}", JSON.toJSONString(msg));
        //数据插入数据库
        userService.asyncAddMessageRecord(msg);

        Channel contactChannel = ChannelBeansCache.get(msg.getContactId());
        if (contactChannel == null) {
            log.info("用户ID：{} 未登录。", msg.getContactId());
            // 缓存到消息队列，等待用户上线
            return;
        }
        Long aLong = userService.getChatList(msg.getUserId(), msg.getContactId());

        User user = userService.getUserInfo(msg.getUserId());
        // 发送消息之前要先发送对话框通知
        ChatDialogResponse chatDialogResponse = ChatDialogResponse.builder()
                .contactId(msg.getUserId())
                .userId(msg.getContactId())
                .chatType(msg.getMsgType())
                .contactName(user.getNickName())
                .avatar(user.getAvatar())
                .msgBody(msg.getMsgBody())
                .msgDate(msg.getMsgDate())
                .isSuccess(true)
                .build();
        contactChannel.writeAndFlush(chatDialogResponse);


        MessageResponse messageResponse = MessageResponse.builder()
                .contactId(msg.getUserId())
                .msgBody(msg.getMsgBody())
                .msgDate(msg.getMsgDate())
                .msgType(msg.getMsgType())
                .build();
        contactChannel.writeAndFlush(messageResponse);
    }
}
