package com.kangec.client.service;

import com.kangec.client.view.contract.LoginContract;
import lombok.extern.slf4j.Slf4j;

/**
 * 上层的界面Presenter服务：
 * 注册到UIService进行服务发现和管理。
 **/

@Slf4j
public class UIService {
    private LoginContract.View loginView;

    public LoginContract.View getLoginView() {
        return loginView;
    }

    public void setLoginView(LoginContract.View loginView) {
        this.loginView = loginView;
    }
}
