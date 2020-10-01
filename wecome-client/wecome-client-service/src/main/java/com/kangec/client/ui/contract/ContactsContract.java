package com.kangec.client.ui.contract;

import com.kangec.client.ui.ui.contact.ContactGroupItem;
import com.kangec.client.ui.ui.contact.ContactGroupList;
import com.kangec.client.ui.ui.contact.ContactItem;
import com.kangec.client.ui.ui.contact.ContactList;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

/**
 * 通讯录MVP协议
 */
public interface ContactsContract {
    interface View {
        void doShow();


        /**
         * 在页面初始化的时候，将群组{@link ContactGroupItem} 添加至
         * 群组列表{@link ContactGroupList}内,并绑定事件处理。
         *
         * @param groupId   群组ID
         * @param groupName 群组名称
         * @param groupHead 群组头像
         */
        void addGroupToContacts(String groupId, String groupName, String groupHead);


        /**
         * 好友推荐 | 默认添加10个好友
         *
         * @param userId       好友ID
         * @param userNickName 好友昵称
         * @param userHead     好友头像
         * @param status       状态；0添加、1允许、2已添加
         */
        void addLuckFriend(String userId, String userNickName, String userHead, Integer status);

        /**
         * 在页面初始化的时候，将联系人信息附着在{@link ContactItem}中，
         * 并将其显示在{@link ContactList}内
         *
         * @param selected     选中;true/false
         * @param userId       好友ID
         * @param userNickName 好友昵称
         * @param userHead     好友头像
         */
        void addContactToContactList(boolean selected, String userId, String userNickName, String userHead);

        /**
         * 搜索联系人成功
         */
        default void onSearchSuccess() {

        }

        /**
         * 搜索联系人失败
         */
        default void onSearchFail() {

        }
    }

    //添加好友
    interface Presenter {
        /**
         * 事件处理；开启与好友发送消息 [点击发送消息时候触发 -> 添加到对话框、选中、展示对话列表]
         *
         * @param userId       用户ID
         * @param contactId    好友ID
         */
        void doEventAddTalkUser(String userId, String contactId);

        /**
         * 事件处理；开启与群组发送消息  [点击发送消息时候触发 -> 添加到对话框、选中、展示对话列表]
         *
         * @param userId  用户ID
         * @param groupId 群组ID
         */
        void doEventAddTalkGroup(String userId, String groupId);

        /**
         * 事件处理；查询有缘用户添加到列表
         *
         * @param userId   用户ID
         * @param listView 用户列表[非必需使用，同步接口可使用]
         */
        void addFriendLuck(String userId, ListView<Pane> listView);


        /**
         * 事件处理；好友搜索[搜索后结果调用添加：addLuckFriend]
         *
         * @param userId 用户ID
         * @param key   搜索关键字
         */
        void doContactSearch(String userId, String key);

        /**
         * 添加好友事件
         *
         * @param userId   个人ID
         * @param contactId 好友ID
         */
        void doEventAddContactUser(String userId, String contactId);

    }
}
