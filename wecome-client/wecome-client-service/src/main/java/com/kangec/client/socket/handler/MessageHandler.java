package com.kangec.client.socket.handler;

import com.alibaba.fastjson.JSON;
import com.kangec.client.service.UIService;
import com.kangec.client.socket.BaseHandler;
import com.kangec.client.view.contract.MainContract;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import packet.message.MessageResponse;

/**
 * @Author Ardien
 * @Date 10/6/2020 4:02 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
@Slf4j
@ChannelHandler.Sharable
public class MessageHandler extends BaseHandler<MessageResponse> {

    public MessageHandler(UIService uiService) {
        super(uiService);
    }

    @Override
    public void channelRead(Channel ctx, MessageResponse msg) {
        log.info("接收消息：{}", JSON.toJSONString(msg));
        MainContract.View mainView = uiService.getMainView();
        Platform.runLater(() -> {
            mainView.addTalkMsgUserLeft(msg.getContactId()
                                        ,msg.getMsgBody()
                                        ,msg.getMsgDate()
                                        ,true
                                        ,false
                                        ,true);
        });
    }
}
