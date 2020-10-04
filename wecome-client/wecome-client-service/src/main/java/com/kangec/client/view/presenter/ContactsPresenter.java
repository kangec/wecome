package com.kangec.client.view.presenter;

import com.kangec.client.view.contract.ContactsContract;
import com.kangec.client.view.ui.contact.ContactPush;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Ardien
 * @Date 9/26/2020 1:18 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Slf4j
public class ContactsPresenter implements ContactsContract.Presenter {
    private ContactsContract.View view;

    public ContactsPresenter(ContactsContract.View view) {
        this.view = view;
    }

    /**
     * 事件处理；开启与好友发送消息 [点击发送消息时候触发 -> 添加到对话框、选中、展示对话列表]
     *
     * @param userId    用户ID
     * @param contactId 好友ID
     */
    @Override
    public void doEventAddTalkUser(String userId, String contactId) {
        log.info("用户 " + contactId + " 已添加到会话界面");
    }

    /**
     * 事件处理；开启与群组发送消息  [点击发送消息时候触发 -> 添加到对话框、选中、展示对话列表]
     *
     * @param userId  用户ID
     * @param groupId 群组ID
     */
    @Override
    public void doEventAddTalkGroup(String userId, String groupId) {
        log.info("群组 " + groupId + " 已添加到会话界面");
    }

    /**
     * 事件处理；查询有缘用户添加到列表
     *
     * @param userId   用户ID
     * @param listView 用户列表[非必需使用，同步接口可使用]
     */
    @Override
    public void addFriendLuck(String userId, ListView<Pane> listView) {
        log.info("新的朋友");
        listView.getItems().add(new ContactPush("1000005", "比丘卡", "8", 0).pane());
        listView.getItems().add(new ContactPush("1000006", "兰兰", "9", 1).pane());
        listView.getItems().add(new ContactPush("1000007", "Alexa", "7", 2).pane());

    }

    /**
     * 事件处理；好友搜索[搜索后结果调用添加：addLuckFriend]
     *
     * @param userId 用户ID
     * @param key    搜索关键字
     */
    @Override
    public void doContactSearch(String userId, String key) {
        log.info("用户 " + userId +" 搜索好友，关键字：" + key);
    }

    /**
     * 添加好友事件
     *
     * @param userId    个人ID
     * @param contactId 好友ID
     */
    @Override
    public void doEventAddContactUser(String userId, String contactId) {
        log.info("用户 " + userId +" 添加" + contactId + "为好友");
    }

    public void MockData() {

        // 群组测试
        view.addGroupToContacts("5307397", "虫洞 · 技术栈(1区)", "6");
        view.addGroupToContacts("5307392", "CSDN 社区专家", "7");
        view.addGroupToContacts("5307399", "洗脚城VIP", "8");

        // 好友测试
        view.addContactToContactList(false, "1000004", "哈尼克兔", "14");
        view.addContactToContactList(false, "1000001", "拎包冲", "12");
        view.addContactToContactList(false, "1000002", "铁锤", "13");
        view.addContactToContactList(true, "1000003", "小傅哥", "1S");

    }
}
