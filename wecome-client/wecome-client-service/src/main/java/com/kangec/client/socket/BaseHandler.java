package com.kangec.client.socket;

import com.kangec.client.service.UIService;
import com.kangec.client.view.contract.MainContract;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseHandler<T> extends SimpleChannelInboundHandler<T> {
    protected UIService uiService;

    public BaseHandler(UIService uiService) {
        this.uiService = uiService;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        log.info("连接异常：{}",cause.getMessage());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
        channelRead(ctx.channel(), msg);
    }

    public abstract void channelRead(Channel ctx, T msg);
}
