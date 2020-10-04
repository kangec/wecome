package com.kangec.client;

import com.kangec.client.service.UIService;
import com.kangec.client.socket.WeComeNettyClient;
import com.kangec.client.view.contract.LoginContract;
import com.kangec.client.view.ui.LoginUI;
import io.netty.channel.Channel;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
public class ClientApplication extends Application{

    private static ExecutorService executorService = Executors.newFixedThreadPool(2);
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    @Override
    public void start(Stage primaryStage) throws Exception {

        LoginContract.View login = new LoginUI();
        UIService uiService = new UIService();
        uiService.setLoginView(login);
        login.doShow();


        // 2. 启动socket连接
        log.info("NettyClient连接服务开始 inetHost：{} inetPort：{}", "127.0.0.1", 9946);
        WeComeNettyClient nettyClient = new WeComeNettyClient(uiService);
        Future<Channel> future = executorService.submit(nettyClient);
        Channel channel = future.get();
        if (null == channel) throw new RuntimeException("netty client start error channel is null");

        while (!nettyClient.isActive()) {
            log.info("NettyClient启动服务 ...");
            Thread.sleep(500);
        }
        log.info("NettyClient连接服务完成 {}", channel.localAddress());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
