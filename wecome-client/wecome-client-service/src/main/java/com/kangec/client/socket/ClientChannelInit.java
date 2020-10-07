package com.kangec.client.socket;

import codec.ObjDecoder;
import codec.ObjEncoder;
import com.kangec.client.service.UIService;
import com.kangec.client.socket.handler.ChatDialogHandler;
import com.kangec.client.socket.handler.LoginHandler;
import com.kangec.client.socket.handler.MessageHandler;
import com.kangec.client.socket.handler.SearchContactHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author Ardien
 * @Date 10/1/2020 1:47 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public class ClientChannelInit extends ChannelInitializer<SocketChannel> {
    private UIService uiService;

    public ClientChannelInit(UIService uiService) {
        this.uiService = uiService;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ObjDecoder());

        pipeline.addLast("loginHandler", new LoginHandler(uiService.getLoginView()));

        pipeline.addLast("chatDialogHandler", new ChatDialogHandler(uiService));

        pipeline.addLast("messageHandler", new MessageHandler(uiService));

        pipeline.addLast("SearchContactHandler", new SearchContactHandler(uiService));

        pipeline.addLast(new ObjEncoder());
    }
}
