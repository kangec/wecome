package com.kangec.contract;

import java.util.Date;

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

        void addTalkMsgRight(String talkId, String msg, Date msgData,
                                    Boolean idxFirst, Boolean selected, Boolean isRemind);

        void addTalkBox(int talkIdx, Integer talkType, String talkId, String talkName,
                        String talkHead, String talkSketch, Date talkDate, Boolean selected);
        void addTalkMsgUserLeft(String talkId, String msg, Date msgDate,
                                Boolean idxFirst, Boolean selected, Boolean isRemind);

        void addTalkMsgGroupLeft(String talkId, String userId, String userNickName,
                                        String userHead, String msg, Date msgDate,
                                        Boolean idxFirst, Boolean selected, Boolean isRemind);

        void addGroupToContacts(String groupId, String groupName, String groupHead);
        void addLuckFriend(String contactId, String userNickName, String userHead, Integer status);
        void addContactToContactList(boolean selected, String userId, String userNickName, String userHead);
        }

    interface Presenter {
        /**
         * 本地客户端窗口退出操作
         */
        void doQuitChat();

        void doSendMessage(String userId, String talkId, Integer talkType, Date msgDate, String msg);

        void doDeleteDialogEvent(String userId, String contactId);

        void doContactSearch(String userId, String text);

        void doEventAddTalkUser(String userId, String userFriendId);

        void doEventAddTalkGroup(String userId, String groupId);

        void doEventAddContactUser(String userId, String contactId);
    }
}
