package com.kangec.client.ui.ui;

import com.kangec.client.ui.common.UIBinding;
import com.kangec.client.ui.contract.ChatContract;
import com.kangec.client.ui.contract.ContactsContract;
import com.kangec.client.ui.contract.MainContract;
import com.kangec.client.ui.presenter.ContactsPresenter;
import com.kangec.client.ui.ui.contact.*;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Ardien
 * @Date 9/24/2020 1:20 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Slf4j
public class ContactsUI extends UIBinding implements ContactsContract.View {
    private ListView<Pane> contactsList;
    private ObservableList<Pane> contactItemsObservable;
    private ListView<Pane> groupListView;
    private ListView<Pane> userListView;
    private ChatContract.View chatView;
    private MainContract.View mainView;
    private ListView<Pane> contactPushListView;
    private ContactsContract.Presenter presenter = new ContactsPresenter(this);


    ContactsUI(Pane contactPane, ChatContract.View chatView) {
        super.root = contactPane;
        this.chatView = chatView;
        initView();
    }

    public ContactsUI(Pane contactsPane, MainUI mainUI, ChatContract.View chatUI) {
        this(contactsPane,chatUI);
        mainView = mainUI;
    }

    @Override
    public void initView() {
        contactsList = binding("contact_list",ListView.class);
        Button minBtn = binding("group_bar_chat_min_c", Button.class);
        Button closeBtn = binding("group_bar_chat_close_c", Button.class);

        contactItemsObservable = contactsList.getItems();


        //1. 通讯录添加 新的朋友框体
        initAddFriendLuck();
        //2. 通讯录添加 公众号框体
        addFriendSubscription();
        //3. 通讯录添加 群组框体
        addFriendGroupList();
        //4. 通讯录添加 好友框体
        addFriendUserList();

        groupListView = (ListView<Pane>) lookup("groupListView");
        userListView = (ListView<Pane>) lookup("userListView");
        contactPushListView = (ListView<Pane>) lookup("friendLuckListView");
        minBtn.setOnAction(event -> setIconified(true));
        closeBtn.setOnAction(event -> {
            close();
            log.info("客户端退出");
            System.exit(0);
        });
    }

    @Override
    public void doShow() {
        show();
    }

    /**
     * 在页面初始化的时候，将群组{@link ContactGroupItem} 添加至
     * 群组列表{@link ContactGroupList}内,并绑定事件处理。
     * @param groupId   群组ID
     * @param groupName 群组名称
     * @param groupHead 群组头像
     */
    @Override
    public void addGroupToContacts(String groupId, String groupName, String groupHead) {
        ContactGroupItem elementFriendGroup = new ContactGroupItem(groupId, groupName, groupHead);
        Pane pane = elementFriendGroup.pane();
        // 添加到群组列表
        ObservableList<Pane> items = groupListView.getItems();
        items.add(pane);
        groupListView.setPrefHeight(80 * items.size());
        ((Pane)lookup("friendGroupList")).setPrefHeight(80 * items.size());

        // 群组，内容框[初始化，未装载]，承载群组信息内容，点击按钮时候填充
        Pane detailContent = new Pane();
        detailContent.setPrefSize(850, 560);
        detailContent.getStyleClass().add("friendGroupDetailContent");
        ObservableList<Node> children = detailContent.getChildren();

        Button sendMsgButton = new Button();
        sendMsgButton.setId(groupId);
        sendMsgButton.getStyleClass().add("friendGroupSendMsgButton");
        sendMsgButton.setPrefSize(176, 50);
        sendMsgButton.setLayoutX(337);
        sendMsgButton.setLayoutY(450);
        sendMsgButton.setText("发送消息");
        doEventOpenFriendGroupSendMsg(sendMsgButton, groupId, groupName, groupHead);
        children.add(sendMsgButton);

        // 添加监听事件
        pane.setOnMousePressed(event -> {
            clearViews(contactsList, userListView);
            setContentPaneBox(groupId,groupName,detailContent);
        });
        setContentPaneBox(groupId,groupName,detailContent);

    }

    /**
     * 好友推荐 | 默认添加10个好友
     *
     * @param contactId       好友ID
     * @param userNickName 好友昵称
     * @param userHead     好友头像
     * @param status       状态；0添加、1允许、2已添加
     */
    @Override
    public void addLuckFriend(String contactId, String userNickName, String userHead, Integer status) {
        ContactPush friendLuckUser = new ContactPush(contactId, userNickName, userHead, status);
        Pane pane = friendLuckUser.pane();
        // 添加到好友列表
        ObservableList<Pane> items = contactPushListView.getItems();
        items.add(pane);
        // 点击事件
        friendLuckUser.statusLabel().setOnMousePressed(event -> {
            presenter.doEventAddContactUser(mainView.getUserId(),contactId);
        });
    }


    /**
     * 在页面初始化的时候，将联系人信息附着在{@link ContactItem}中，
     * 并将其显示在{@link ContactList}内
     *
     * @param selected     选中;true/false
     * @param userId       好友ID
     * @param userNickName 好友昵称
     * @param userHead     好友头像
     */
    @Override
    public void addContactToContactList(boolean selected, String userId, String userNickName, String userHead) {
        ContactItem friendUser = new ContactItem(userId, userNickName, userHead);
        Pane pane = friendUser.pane();
        // 添加到好友列表
        ObservableList<Pane> items = userListView.getItems();
        items.add(pane);
        userListView.setPrefHeight(80 * items.size());
        ((Pane)lookup("friendUserList")).setPrefHeight(80 * items.size());
        // 选中
        if (selected) {
            userListView.getSelectionModel().select(pane);
        }

        // 好友，内容框[初始化，未装载]，承载好友信息内容，点击按钮时候填充
        Pane detailContent = new Pane();
        detailContent.setPrefSize(850, 560);
        detailContent.getStyleClass().add("friendUserDetailContent");
        ObservableList<Node> children = detailContent.getChildren();

        Button sendMsgButton = new Button();
        sendMsgButton.setId(userId);
        sendMsgButton.getStyleClass().add("friendUserSendMsgButton");
        sendMsgButton.setPrefSize(176, 50);
        sendMsgButton.setLayoutX(337);
        sendMsgButton.setLayoutY(450);
        sendMsgButton.setText("发送消息");
        doEventOpenFriendUserSendMsg(sendMsgButton, userId, userNickName, userHead);
        children.add(sendMsgButton);

        // 添加监听事件
        pane.setOnMousePressed(event -> {
            clearViews(contactsList, groupListView);
            setContentPaneBox(userId, userNickName,detailContent);
        });
        setContentPaneBox(userId, userNickName,detailContent);
    }

    private Node lookup(String id) {
        Node node = null;
        for (Pane pane : contactItemsObservable) {
            node = pane.lookup("#" + id);
            if (node != null) break;
        }
        return node;
    }
    /**
     * 好友列表添加工具方法‘新的朋友’
     */
    private void initAddFriendLuck() {
        ContactTag elementFriendTag = new ContactTag("新的朋友");
        contactItemsObservable.add(elementFriendTag.pane());

        OperationContact element = new OperationContact();
        Pane pane = element.pane();
        contactItemsObservable.add(pane);

        // 面板填充和事件
        pane.setOnMousePressed(event -> {
            Pane luckPane = element.friendLuckPane();
            setContentPaneBox("ContactPush","新的好友",luckPane);
            clearViews(userListView, groupListView);
            ListView<Pane> luckListView = element.friendLuckListView();
            luckListView.getItems().clear();

            log.info("添加好友");
        });
        // 搜索框事件
        TextField friendLuckSearch = element.friendLuckSearch();

        // 键盘事件；搜索好友
        friendLuckSearch.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                String text = friendLuckSearch.getText();
                if (null == text) text = "";
                if (text.length() > 30) text = text.substring(0, 30);
                text = text.trim();
                // TODO 搜索好友
                presenter.doContactSearch(mainView.getUserId(), text);

                // 搜索清空元素
                element.friendLuckListView().getItems().clear();

                // 添加朋友
                element.friendLuckListView().getItems().add(new ContactPush("1000005", "比丘卡", "11", 0).pane());
                element.friendLuckListView().getItems().add(new ContactPush("1000006", "兰兰", "14", 1).pane());
                element.friendLuckListView().getItems().add(new ContactPush("1000007", "Alexa", "6", 2).pane());

            }
        });


    }

    /**
     * 好友列表添加‘公众号’
     */
    private void addFriendSubscription() {
        ContactTag elementFriendTag = new ContactTag("公众号");
        contactItemsObservable.add(elementFriendTag.pane());

        ContactSubscription element = new ContactSubscription();
        Pane pane = element.pane();
        contactItemsObservable.add(pane);

        pane.setOnMousePressed(event -> {
            clearViews(userListView,groupListView);
            setContentPaneBox("Subscription","公众号",element.subPane());
        });
    }

    /**
     * 好友群组框体
     */
    private void addFriendGroupList() {
        ContactTag elementFriendTag = new ContactTag("群聊");
        contactItemsObservable.add(elementFriendTag.pane());

        ContactGroupList element = new ContactGroupList();
        Pane pane = element.pane();
        contactItemsObservable.add(pane);
    }

    /**
     * 好友框体
     */
    private void addFriendUserList() {
        ContactTag elementFriendTag = new ContactTag("好友");
        contactItemsObservable.add(elementFriendTag.pane());

        ContactList element = new ContactList();
        Pane pane = element.pane();
        contactItemsObservable.add(pane);
    }

    /**
     * group_bar_chat：填充对话列表 & 对话框名称
     *
     * @param id   用户、群组等ID
     * @param name 用户、群组等名称
     * @param node 展现面板
     */
    void setContentPaneBox(String id, String name, Node node) {
        // 填充对话列表
        Pane content_pane_box = binding("content_pane_box", Pane.class);
        content_pane_box.setUserData(id);
        content_pane_box.getChildren().clear();
        content_pane_box.getChildren().add(node);
        // 对话框名称
        Label info_name = binding("content_name", Label.class);
        info_name.setText(name);
    }

    // 好友；开启与好友发送消息 [点击发送消息时候触发 -> 添加到对话框、选中、展示对话列表]
    public void doEventOpenFriendUserSendMsg(Button sendMsgButton, String userFriendId,
                                             String userFriendNickName, String userFriendHead) {
        sendMsgButton.setOnAction(event -> {
            // 1. 添加好友到对话框
            chatView.addTalkBox(0, 0, userFriendId, userFriendNickName, userFriendHead, null, null, true);
            // 2. 切换对话框窗口
                // 切换到聊天界面
            mainView.switchBarChats(true);
                // 隐藏通讯录界面
            mainView.switchBarContacts(false);
            // TODO 3. 事件处理；填充到对话框
            presenter.doEventAddTalkUser(mainView.getUserId(),userFriendId);
        });
    }

    // 群组；开启与群组发送消息
    public void doEventOpenFriendGroupSendMsg(Button sendMsgButton, String groupId, String groupName, String groupHead) {
        sendMsgButton.setOnAction(event -> {
            // 1. 添加好友到对话框
            chatView.addTalkBox(0, 1, groupId, groupName, groupHead, null, null, true);
            // 2. 切换对话框窗口
            // 切换到聊天界面
            mainView.switchBarChats(true);
            // 隐藏通讯录界面
            mainView.switchBarContacts(false);
            // TODO 3. 事件处理；填充到对话框
            presenter.doEventAddTalkGroup(mainView.getUserId(), groupId);
        });
    }
}
