package com.kangec.client.socket.handler;

import com.kangec.client.service.UIService;
import com.kangec.client.socket.BaseHandler;
import com.kangec.client.view.contract.MainContract;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import packet.contact.AddContactResponse;

/**
 * @Author Ardien
 * @Date 10/8/2020 9:59 AM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Slf4j
@ChannelHandler.Sharable
public class AddContactHandler extends BaseHandler<AddContactResponse> {

    public AddContactHandler(UIService uiService) {
        super(uiService);
    }

    @Override
    public void channelRead(Channel ctx, AddContactResponse response) {
        MainContract.View view = uiService.getMainView();
        Platform.runLater(() -> {
            view.addContactToContactList(true
                                        , response.getContactId()
                                        , response.getNickName()
                                        , response.getAvatar());
        });
    }
}
