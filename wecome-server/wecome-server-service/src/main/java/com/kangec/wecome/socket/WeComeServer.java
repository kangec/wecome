package com.kangec.wecome.socket;


import com.kangec.wecome.service.UserService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

@Service
@Slf4j
public class WeComeServer implements Callable<Channel> {

    private UserService userService;
    private int DEFAULT_PORT = 9946;
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup wokerGroup = new NioEventLoopGroup();
    private Channel channel;
    @Override
    public Channel call() throws Exception {
        return start();
    }

    /**
     * Netty服务启动配置引导
     * @return 等待链接的通道
     */
    private Channel start() {
        ChannelFuture future = null;
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, wokerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childHandler(new WeComeChannelInitializer(userService));
            future = serverBootstrap.bind(DEFAULT_PORT).syncUninterruptibly();
            channel = future.channel();
        }catch (Exception exception) {
            log.error("Server start error: {}",exception.getMessage());
        } finally {
            if (future!=null && future.isSuccess()) {
                log.info("Netty 服务端启动成功，端口：" + DEFAULT_PORT);
            } else {
                log.info("Netty 服务端启动失败，端口：" + DEFAULT_PORT);
            }
        }
        return channel;
    }

    // 资源释放
    public void destroy() {
        if (channel == null) return;
        channel.close();
        bossGroup.shutdownGracefully();
        wokerGroup.shutdownGracefully();
    }

    public  void setDefaultPort(int port) {
        this.DEFAULT_PORT = port;
    }

    public Channel getChannel() {
        return channel;
    }

    public  int getPort() {
        return DEFAULT_PORT;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
