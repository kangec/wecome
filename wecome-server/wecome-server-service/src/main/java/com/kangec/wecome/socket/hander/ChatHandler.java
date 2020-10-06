package com.kangec.wecome.socket.hander;

import com.alibaba.fastjson.JSON;
import com.kangec.wecome.config.ChannelBeansCache;
import com.kangec.wecome.service.UserService;
import com.kangec.wecome.socket.BaseHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import packet.chat.ChatDialogRequest;
import packet.chat.ChatDialogResponse;
import utils.StatusCode;

/**
 * @Author Ardien
 * @Date 10/6/2020 7:19 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@ChannelHandler.Sharable
@Slf4j
public class ChatHandler extends BaseHandler<ChatDialogRequest> {

    public ChatHandler(UserService userService) {
        super(userService);
    }

    @Override
    public void channelRead(Channel ctx, ChatDialogRequest msg) {
        log.info("对话框处理: " + msg.getAction() +" {} ", JSON.toJSONString(msg));
        userService.asyncResolveChatRecord(msg);
    }
}
