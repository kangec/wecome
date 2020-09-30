package com.kangec.contract;

/**
 * @Author Ardien
 * @Date 9/22/2020 8:43 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public interface LoginContract {
    interface Presenter {
        public void login(String username, String password);
    }

    interface View {

        void doShow();
        void onUserNameError();
        void onPasswordError();

        /**
         * 登录事件
         */
        void onStartLogin();

        /**
         * 登陆成功；跳转聊天窗口
         */
        void onLoginSuccess();

        /**
         * 登录失败：弹窗提示用户
         */
        void onLoginFailed();
    }
}
