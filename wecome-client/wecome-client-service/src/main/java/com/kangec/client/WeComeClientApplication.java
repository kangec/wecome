package com.kangec.client;

import com.kangec.client.service.UIService;
import com.kangec.client.socket.WeComeNettyClient;
import com.kangec.client.view.common.CacheUtil;
import com.kangec.client.view.contract.LoginContract;
import com.kangec.client.view.ui.LoginUI;
import io.netty.channel.Channel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import packet.reconnect.ReconnectRequest;

import java.util.concurrent.*;

@Slf4j
public class WeComeClientApplication extends Application{

    private static ExecutorService executorService = Executors.newFixedThreadPool(2);
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private WeComeNettyClient nettyClient = null;
    private UIService uiService = null;
    private volatile boolean isSuccess = false;

    @Override
    public void start(Stage primaryStage) throws Exception {

        LoginContract.View login = new LoginUI();
        uiService = new UIService();
        uiService.setLoginView(login);
        login.doShow();

        // 2. 启动socket连接
        log.info("NettyClient连接服务开始 inetHost：{} inetPort：{}", "127.0.0.1", 9946);
        nettyClient = new WeComeNettyClient(uiService);
        Future<Channel> future = executorService.submit(nettyClient);
        Channel channel = future.get();
        if (null == channel) throw new RuntimeException("netty client start error channel is null");

        while (!nettyClient.isActive()) {
            log.info("NettyClient启动服务 ...");
            TimeUnit.SECONDS.sleep(2);
        }
        log.info("NettyClient连接服务完成 {}", channel.localAddress());

        // 断线重连
        scheduledExecutorService.scheduleAtFixedRate(this::reconnect, 4, 6, TimeUnit.SECONDS);
    }

    private void reconnect() {
        while (!nettyClient.isActive()) {
            System.out.println("通信管道巡检：通信管道状态 " + nettyClient.isActive());
            try {
                System.out.println("通信管道巡检：断线重连[Begin]");
                Channel freshChannel = executorService.submit(nettyClient).get();
                if (null == CacheUtil.userId) continue;
                freshChannel.writeAndFlush(new ReconnectRequest(CacheUtil.userId));
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("通信管道巡检：断线重连[Error]");
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
