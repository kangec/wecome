package com.kangec.wecome;

import com.kangec.wecome.socket.WeComeServer;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class WeComeServerApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private WeComeServer weComeServer;

    public static void main(String[] args) {
        SpringApplication.run(WeComeServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("WeCome Netty Server 开始启动，端口：" + weComeServer.getPort());
        Future<Channel> future = Executors.newCachedThreadPool().submit(weComeServer);
        Channel channel = future.get();
        if (channel == null) {
            throw new RuntimeException("WeCome Netty Server 启动失败：Channel is null.");
        }
        while (!channel.isActive()) {
            log.info("WeCome Netty Server 正在启动中");
            Thread.sleep(1000);
        }
        log.info("WeCome Netty Server 启动成功 {}",channel.localAddress());
    }
}
