package com.kangec.client.service;

import com.kangec.client.ui.contract.ChatContract;
import com.kangec.client.ui.contract.ContactsContract;
import com.kangec.client.ui.contract.LoginContract;
import com.kangec.client.ui.contract.MainContract;
import lombok.extern.slf4j.Slf4j;

/**
 * 上层的界面Presenter服务：
 * 注册到UIService进行服务发现和管理。
 **/

@Slf4j
public class UIService {
    private ChatContract.Presenter chatPresenter;
    private ContactsContract.Presenter contactPresenter;
    private LoginContract.Presenter loginPresenter;
    private MainContract.Presenter mainPresenter;

    public ContactsContract.Presenter getContactPresenter() {
        return contactPresenter;
    }

    public void setContactPresenter(ContactsContract.Presenter contactPresenter) {
        log.info("ContactsContract.Presenter 已初始化，ID：" + contactPresenter.getClass());
        this.contactPresenter = contactPresenter;
    }

    public LoginContract.Presenter getLoginPresenter() {
        return loginPresenter;
    }

    public void setLoginPresenter(LoginContract.Presenter loginPresenter) {
        log.info("LoginContract.Presenter 已初始化，ID：" + loginPresenter.getClass());
        this.loginPresenter = loginPresenter;
    }

    public MainContract.Presenter getMainPresenter() {
        return mainPresenter;
    }

    public void setMainPresenter(MainContract.Presenter mainPresenter) {
        log.info("MainContract.Presenter已初始化，ID：" + mainPresenter.getClass());
        this.mainPresenter = mainPresenter;
    }

    public ChatContract.Presenter getChatPresenter() {
        return chatPresenter;
    }

    public void setChatPresenter(ChatContract.Presenter chatPresenter) {
        log.info("ChatContract.Presenter 已初始化，ID：" + chatPresenter.getClass());
        this.chatPresenter = chatPresenter;
    }
}
