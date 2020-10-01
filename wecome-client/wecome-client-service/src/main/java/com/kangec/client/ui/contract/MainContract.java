package com.kangec.client.ui.contract;

/**
 * @Author Ardien
 * @Date 9/24/2020 10:10 AM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public interface MainContract {
    interface View {
        void doShow();
        void setUserInfo(String userId, String userNickName, String userHead);
        void switchBarChats(boolean flag);
        void switchBarContacts(boolean flag);
        String getUserId();
    }

    interface Presenter {
        /**
         * 本地客户端窗口退出操作
         */
        void doQuitChat();
    }
}
