package com.kangec.client.server;

import com.kangec.client.cache.Beans;
import com.kangec.client.handler.WeComeChannelInit;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * WeCome真正的客户端，处理收发消息网络连接。
 *
 * @Author Ardien
 * @Date 9/26/2020 10:00 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
@Slf4j
public class WeComeNettyClient implements Callable<ChannelFuture> {

    private String DEFAULT_HOST = "127.0.0.1";
    private int DEFAULT_PORT = 9946;
    private Channel channel = null;

    public void setHostName(String hostName, int port) {
        DEFAULT_HOST = hostName;
        DEFAULT_PORT = port;
    }

    public boolean isActive() {
        return channel.isActive();
    }

    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    @Override
    public ChannelFuture call() throws Exception {
        return run();
    }

    private ChannelFuture run() {
        ChannelFuture future = null;
        try {
            Bootstrap client = new Bootstrap();
            client.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.AUTO_READ, true)
                    .handler(new WeComeChannelInit());
            future = client.connect(DEFAULT_HOST, DEFAULT_PORT).sync();
            future = future.channel().closeFuture().sync();
            this.channel = future.channel();
            Beans.addBean(Beans.CLIENT_CHANNEL,channel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (future != null && future.isSuccess()) {
                log.info("客户端连接服务器成功。");
            }else {
                log.info("客户端连接服务器失败。");
            }
            workerGroup.shutdownGracefully();
        }

        return future;
    }
}
