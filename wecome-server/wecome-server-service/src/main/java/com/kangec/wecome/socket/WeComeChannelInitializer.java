package com.kangec.wecome.socket;

import com.kangec.wecome.service.UserService;
import com.kangec.wecome.socket.hander.LoginHandler;
import io.netty.channel.ChannelInitializer;
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
        ch.pipeline()
                .addLast(new LoginHandler(userService));
    }
}
