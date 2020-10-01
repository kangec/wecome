package com.kangec.client.ui.presenter;

import com.kangec.client.cache.Beans;
import com.kangec.client.ui.common.StringUtils;
import com.kangec.client.ui.contract.LoginContract;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import packet.login.LoginRequest;
import packet.login.LoginResponse;


@Slf4j
public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View loginUI;

    public LoginPresenter(LoginContract.View loginUI) {
        this.loginUI = loginUI;
    }

    /**
     * 交付Service进行处理
     * @param username 用户名
     * @param password 密码
     */
    @Override
    public void login(String username, String password) {
        if (StringUtils.isValidUsername(username)) {
            if (StringUtils.isValidPassword(password)) {
                log.info("用户登录事件  ===>>  " + "用户名:" + username + "\t密码： " + password);
                // 通知视图层，开始进行登录操作
                loginUI.onStartLogin();
                doLogin(username,password);
            }else {
                loginUI.onPasswordError();
                log.info("密码校验失败：密码不合法。");
            }
        } else {
            loginUI.onUserNameError();
            log.info("用户名校验失败：用户名不合法。");
        }
    }

    @Override
    public void loginSuccess(LoginResponse msg) {
        loginUI.onLoginSuccess(msg);
    }

    @Override
    public void loginFail() {
        loginUI.onLoginFailed();
    }

    //
    private void doLogin(String username, String password) {
        Channel channel = Beans.getBean(Beans.CLIENT_CHANNEL, Channel.class);
        LoginRequest loginRequest = new LoginRequest(username,password);
        channel.writeAndFlush(loginRequest);
    }


}
