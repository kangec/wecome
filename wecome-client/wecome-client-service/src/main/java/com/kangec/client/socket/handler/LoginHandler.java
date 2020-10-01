package com.kangec.client.socket.handler;

import com.alibaba.fastjson.JSON;
import com.kangec.client.service.UIService;
import com.kangec.client.ui.contract.LoginContract;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import packet.login.LoginResponse;

@Slf4j
public class LoginHandler extends SimpleChannelInboundHandler<LoginResponse> {
    private UIService uiService;

    public LoginHandler(UIService uiService) {
        this.uiService = uiService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponse msg) throws Exception {
        LoginContract.Presenter loginPresenter = uiService.getLoginPresenter();
        log.info(JSON.toJSONString(msg));
        if (msg.isIdSuccess()) {
            log.info("登录成功");
            Platform.runLater(()->loginPresenter.loginSuccess(msg));
        } else {
            log.info("登录失败，检查账号密码");
            Platform.runLater(loginPresenter::loginFail);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.info(cause.getMessage());
    }
}
