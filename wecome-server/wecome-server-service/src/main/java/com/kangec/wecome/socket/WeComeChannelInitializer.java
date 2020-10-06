package com.kangec.wecome.socket;

import codec.ObjDecoder;
import codec.ObjEncoder;
import com.kangec.wecome.service.UserService;
import com.kangec.wecome.socket.hander.ChatHandler;
import com.kangec.wecome.socket.hander.LoginHandler;
import com.kangec.wecome.socket.hander.MessageHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author Ardien
 * @Date 10/1/2020 12:51 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public class WeComeChannelInitializer extends ChannelInitializer<SocketChannel> {
    private UserService userService;

    public WeComeChannelInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ObjDecoder());

        pipeline.addLast(new LoginHandler(userService));

        pipeline.addLast(new ChatHandler(userService));

        pipeline.addLast(new MessageHandler(userService));

        pipeline.addLast(new ObjEncoder());
    }
}
