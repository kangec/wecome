package com.kangec.client.socket.handler;

import com.kangec.client.service.UIService;
import com.kangec.client.socket.BaseHandler;
import com.kangec.client.view.contract.MainContract;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import packet.chat.ChatDialogResponse;

/**
 * @Author Ardien
 * @Date 10/6/2020 8:53 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Slf4j
@ChannelHandler.Sharable
public class ChatDialogHandler extends BaseHandler<ChatDialogResponse> {
    public ChatDialogHandler(UIService uiService) {
        super(uiService);
    }

    @Override
    public void channelRead(Channel ctx, ChatDialogResponse msg) {
        MainContract.View mainView = uiService.getMainView();
        Platform.runLater(() -> {
            mainView.addTalkBox(-1, 0
                    , msg.getContactId()
                    , msg.getContactName()
                    , msg.getAvatar()
                    , msg.getMsgBody()
                    , msg.getMsgDate(), false);
        });
    }
}
