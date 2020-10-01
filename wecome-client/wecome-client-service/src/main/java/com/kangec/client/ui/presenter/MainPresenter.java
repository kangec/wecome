package com.kangec.client.ui.presenter;

import com.kangec.client.ui.contract.MainContract;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Ardien
 * @Date 9/26/2020 1:19 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Slf4j
public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    /**
     * 本地客户端窗口退出操作
     */
    @Override
    public void doQuitChat() {
        log.info("系统退出");
    }
}
