package com.kangec.client.ui.contract;

import packet.login.LoginResponse;

/**
 * @Author Ardien
 * @Date 9/22/2020 8:43 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public interface LoginContract {
    interface Presenter {
        public void login(String username, String password);

        void loginSuccess(LoginResponse msg);

        void loginFail();
    }

    interface View {

        void doShow();
        void onUserNameError();
        void onPasswordError();
        LoginContract.Presenter getPresenter();

        /**
         * 登录事件
         */
        void onStartLogin();

        /**
         * 登陆成功；跳转聊天窗口
         * @param msg
         */
        void onLoginSuccess(LoginResponse msg);

        /**
         * 登录失败：弹窗提示用户
         */
        void onLoginFailed();
    }
}
