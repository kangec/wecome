package com.kangec.client.socket.handler;

import com.alibaba.fastjson.JSON;
import com.kangec.client.cache.Beans;

import com.kangec.client.view.contract.LoginContract;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import packet.login.LoginRequest;
import packet.login.LoginResponse;
import packet.login.dto.ContactItemDTO;

import java.util.List;

@Slf4j
public class LoginHandler extends SimpleChannelInboundHandler<LoginResponse> {
    private LoginContract.View view;

    public LoginHandler(LoginContract.View view) {
        this.view = view;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponse msg) throws Exception {
        List<ContactItemDTO> contactList = msg.getContactList();
        log.info(JSON.toJSONString(msg));
        if (msg.isIdSuccess()) {
            log.info("登录成功");
            Platform.runLater(()-> {
                view.onLoginSuccess(msg);

            });
        } else {
            log.info("登录失败，检查账号密码");
            Platform.runLater(view::onLoginFailed);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.info(cause.getMessage());
    }
}
