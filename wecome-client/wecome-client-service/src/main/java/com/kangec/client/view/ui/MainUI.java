package com.kangec.client.view.ui;

import com.kangec.client.view.common.CacheUtil;
import com.kangec.client.view.common.UIBinding;
import com.kangec.client.view.contract.MainContract;
import com.kangec.client.view.presenter.MainPresenter;
import com.kangec.client.view.ui.chats.*;
import com.kangec.client.view.ui.contact.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import packet.login.LoginResponse;
import packet.chat.dto.ChatItemDTO;
import packet.contact.dto.ContactItemDTO;
import packet.contact.dto.GroupItemDTO;
import packet.message.dto.MessagePaneDTO;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Author Ardien
 * @Date 9/24/2020 9:58 AM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Slf4j
@Getter
public class MainUI extends UIBinding implements MainContract.View {
    private static final String RESOURCE_NAME_CHAT_UI = "/view/chat/ChatUI.fxml";
    private MainContract.Presenter presenter = new MainPresenter(this);
    private Pane mainPane;          // 主界面
    private Pane contactsPane;      // 主界面内联系人界面
    private Pane chatsPane;         // 主界面内会话界面

    private Button contactsBtn;     // 切换至通讯录按钮
    private Button avatarBtn;       // 头像按钮
    private Button chatsBtn;        // 切换至会话界面按钮
    private Button fileBtn;         // 切换至文件界面按钮
    private Button settingBtn;      // 设置界面按钮
    private Button minBtn;          // 最小化按钮
    private Button closeBtn;        // 关闭按钮

    private String userId;
    private String userNickName;
    private String userHead;

    // 会话界面控件
    private TextArea txt_input;             // 输入框
    private Label sendLabel;                // 发送按钮
    private ListView<Pane> chatsListView;   //会话列表;

    // 通讯录界面控件
    private ListView<Pane> contactsList;
    private ObservableList<Pane> contactItemsObservable;
    private ListView<Pane> groupListView;
    private ListView<Pane> userListView;
    private ListView<Pane> contactPushListView;

    public MainUI() {
        initView();
        viewBinding();
        move();
    }

    /**
     * 初始化视图
     */
    @Override
    public void initView() {
        try {
            mainPane = FXMLLoader.load(getClass().getResource(RESOURCE_NAME_CHAT_UI));
            super.root = mainPane;
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(mainPane);
        scene.setFill(Color.TRANSPARENT);
        setScene(scene);
        initStyle(StageStyle.TRANSPARENT);
        setResizable(false);
        getIcons().add(new Image("/icon/icon_wecome.png"));
        setTitle("WeCome");
    }

    void viewBinding() {
        chatsPane = binding("pane_chat", Pane.class);
        contactsPane = binding("pane_contact", Pane.class);
        avatarBtn = binding("bar_avatar", Button.class);
        settingBtn = binding("bar_setting", Button.class);
        fileBtn = binding("bar_files", Button.class);
        chatsBtn = binding("bar_chats", Button.class);
        contactsBtn = binding("bar_contacts", Button.class);
        minBtn = binding("group_bar_chat_min", Button.class);
        closeBtn = binding("group_bar_chat_close", Button.class);
        chatsListView = binding("talkList", ListView.class);
        sendLabel = binding("touch_send", Label.class);
        txt_input = binding("txt_input", TextArea.class);
        contactsList = binding("contact_list", ListView.class);
        contactItemsObservable = contactsList.getItems();


        minBtn.setOnAction(event -> setIconified(true));
        closeBtn.setOnAction(event -> {
            close();
            log.info("客户端退出");
            System.exit(0);
        });

        barChat();
        barFriend();
        sendMessageEntry();
        sendMessageMousePressed();

        //1. 通讯录添加 新的朋友框体
        initAddFriendLuck();
        //2. 通讯录添加 公众号框体
        addFriendSubscription();
        //3. 通讯录添加 群组框体
        addFriendGroupList();
        //4. 通讯录添加 好友框体
        addFriendUserList();


/*        groupListView = binding("groupListView", ListView.class);
        userListView = binding("userListView", ListView.class);
        contactPushListView = binding("friendLuckListView", ListView.class);*/
        groupListView = (ListView<Pane>) lookup("groupListView");
        userListView = (ListView<Pane>) lookup("userListView");
        contactPushListView = (ListView<Pane>) lookup("friendLuckListView");
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
            setContentPaneBox("ContactPush", "新的好友", luckPane);
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
                presenter.doContactSearch(userId, text);

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
            clearViews(userListView, groupListView);
            setContentPaneBox("Subscription", "公众号", element.subPane());
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
    // 监听键盘Entry事件
    private void sendMessageEntry() {
        txt_input.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                doEventSendMsg();
            }
        });
    }

    // 监听发送按钮的鼠标点击事件
    private void sendMessageMousePressed() {
        sendLabel.setOnMousePressed(event -> {
            doEventSendMsg();
        });
    }

    private void doEventSendMsg() {
        MultipleSelectionModel<Pane> selectionModel = chatsListView.getSelectionModel();
        Pane selectedItem = selectionModel.getSelectedItem();
        // 对话信息
        // 不点击会话列表时 会产生空指针异常
        if (selectedItem == null) return;
        ChatItem chatItem = (ChatItem) selectedItem.getUserData();
        String msg = txt_input.getText();
        if (null == msg || "".equals(msg) || "".equals(msg.trim())) {
            return;
        }
        Date msgDate = new Date();
        // TODO 发送消息
        presenter.doSendMessage(userId, chatItem.getTalkId(), chatItem.getTalkType(), msgDate, msg);

        // 发送事件给自己添加消息
        addTalkMsgRight(chatItem.getTalkId(), msg, msgDate, true, true, false);
        txt_input.clear();
    }

    @Override
    public void addTalkBox(int talkIdx, Integer talkType, String talkId, String talkName,
                           String talkHead, String talkSketch, Date talkDate, Boolean selected) {
        // 填充到对话框
        // 判断会话框是否有该对象
        ChatContactPane elementTalk = CacheUtil.talkMap.get(talkId);
        if (null != elementTalk) {
            Node talkNode = chatsListView.lookup("#" + ElementIds.ElementTalkId.createTalkPaneId(talkId));
            if (null == talkNode) {
                chatsListView.getItems().add(talkIdx, elementTalk.pane());
                // 填充对话框消息栏
                fillInfoBox(elementTalk, talkName);
            }
            if (selected) {
                // 设置选中
                chatsListView.getSelectionModel().select(elementTalk.pane());
            }
            // 填充对话框消息栏
            fillInfoBox(elementTalk, talkName);
            return;
        }
        // 初始化对话框元素
        ChatContactPane talkElement = new ChatContactPane(talkId, talkType, talkName, talkHead,
                talkSketch, talkDate);
        CacheUtil.talkMap.put(talkId, talkElement);
        // 填充到对话框
        ObservableList<Pane> items = chatsListView.getItems();
        Pane talkElementPane = talkElement.pane();
        if (talkIdx >= 0) {
            items.add(talkIdx, talkElementPane);  // 添加到第一个位置
        } else {
            items.add(talkElementPane);           // 顺序添加
        }
        if (selected) {
            chatsListView.getSelectionModel().select(talkElementPane);
        }
        // 对话框元素点击事件
        talkElementPane.setOnMousePressed(event -> {
            // 填充消息栏
            fillInfoBox(talkElement, talkName);
            // 清除消息提醒
            Label msgRemind = talkElement.msgRemind();
            msgRemind.setUserData(new RemindCount(0));
            msgRemind.setVisible(false);
        });
        // 鼠标事件[移入/移出]
        talkElementPane.setOnMouseEntered(event -> {
            talkElement.delete().setVisible(true);
        });
        talkElementPane.setOnMouseExited(event -> {
            talkElement.delete().setVisible(false);
        });
        // 填充对话框消息栏
        fillInfoBox(talkElement, talkName);
        // 从对话框中删除
        talkElement.delete().setOnMouseClicked(event -> {
            chatsListView.getItems().remove(talkElementPane);
            binding("info_pane_box", Pane.class).getChildren().clear();
            binding("info_pane_box", Pane.class).setUserData(null);
            binding("info_name", Label.class).setText("");
            talkElement.getData().getItems().clear();
            talkElement.clearMsgSketch();

            // TODO 对话框删除
            presenter.doDeleteDialogEvent(userId, talkId);
        });
    }

    /**
     * 私有方法
     * 填充对话框消息内容
     *
     * @param talkElement 对话框元素
     * @param talkName    对话框名称
     */
    private void fillInfoBox(ChatContactPane talkElement, String talkName) {
        String talkId = talkElement.pane().getUserData().toString();
        // 填充对话列表
        Pane info_pane_box = binding("info_pane_box", Pane.class);
        String boxUserId = (String) info_pane_box.getUserData();
        // 判断是否已经填充[talkId]，当前已填充则返回
        if (talkId.equals(boxUserId)) return;
        ListView<Pane> listView = talkElement.getData();
        info_pane_box.setUserData(talkId);
        info_pane_box.getChildren().clear();
        info_pane_box.getChildren().add(listView);
        // 对话框名称
        Label info_name = binding("info_name", Label.class);
        info_name.setText(talkName);
    }

    @Override
    public void addTalkMsgUserLeft(String talkId, String msg, Date msgDate,
                                   Boolean idxFirst, Boolean selected, Boolean isRemind) {
        ChatContactPane talkElement = CacheUtil.talkMap.get(talkId);
        ListView<Pane> listView = talkElement.getData();
        ChatItemData talkUserData = (ChatItemData) listView.getUserData();
        Pane left = new MessagePane().left(talkUserData.getTalkName(), talkUserData.getTalkHead(), msg);
        // 消息填充
        listView.getItems().add(left);
        // 滚动条
        listView.scrollTo(left);
        talkElement.fillMsgSketch(msg, msgDate);
        // 设置位置&选中
        updateTalkListIdxAndSelected(0, talkElement.pane(), talkElement.msgRemind(),
                idxFirst, selected, isRemind);
        // 填充对话框聊天窗口
        fillInfoBox(talkElement, talkUserData.getTalkName());
    }

    @Override
    public void addTalkMsgGroupLeft(String talkId, String userId, String userNickName,
                                    String userHead, String msg, Date msgDate,
                                    Boolean idxFirst, Boolean selected, Boolean isRemind) {
        // 自己的消息抛弃
        if (this.userId.equals(userId)) return;
        ChatContactPane talkElement = CacheUtil.talkMap.get(talkId);
        if (null == talkElement) {
            ChatGroupsData groupsData = (ChatGroupsData) binding(ElementIds.ElementTalkId.createFriendGroupId(talkId), Pane.class).getUserData();
            if (null == groupsData) return;

            addTalkBox(0, 1, talkId, groupsData.getGroupName()
                    , groupsData.getGroupHead(), userNickName + "：" + msg, msgDate, false);

            talkElement = CacheUtil.talkMap.get(talkId);
            // 事件通知(开启与群组发送消息)
            System.out.println("事件通知(开启与群组发送消息)");
        }
        ListView<Pane> listView = talkElement.getData();
        ChatItemData talkData = (ChatItemData) listView.getUserData();
        Pane left = new MessagePane().left(userNickName, userHead, msg);
        // 消息填充
        listView.getItems().add(left);
        // 滚动条
        listView.scrollTo(left);
        talkElement.fillMsgSketch(userNickName + "：" + msg, msgDate);
        // 设置位置&选中
        updateTalkListIdxAndSelected(1, talkElement.pane(), talkElement.msgRemind(), idxFirst, selected, isRemind);
        // 填充对话框聊天窗口
        fillInfoBox(talkElement, talkData.getTalkName());
    }

    @Override
    public void addTalkMsgRight(String talkId, String msg, Date msgData,
                                Boolean idxFirst, Boolean selected, Boolean isRemind) {
        ChatContactPane talkElement = CacheUtil.talkMap.get(talkId);
        ListView<Pane> listView = talkElement.getData();
        Pane right = new MessagePane().right(userNickName, userHead, msg);
        // 消息填充
        listView.getItems().add(right);
        // 滚动条
        listView.scrollTo(right);
        talkElement.fillMsgSketch(msg, msgData);
        // 设置位置&选中
        updateTalkListIdxAndSelected(0, talkElement.pane(), talkElement.msgRemind(), idxFirst, selected, isRemind);
    }

    /**
     * 更新对话框列表元素位置指定并选中[在聊天消息发送时触达]
     */
    /**
     * @param talkType        对话框类型[0好友、1群组]
     * @param talkElementPane 对话框元素面板
     * @param msgRemindLabel  消息提醒标签
     * @param idxFirst        是否设置首位
     * @param selected        是否选中
     * @param isRemind        是否提醒
     */
    void updateTalkListIdxAndSelected(int talkType, Pane talkElementPane, Label msgRemindLabel, Boolean idxFirst,
                                      Boolean selected, Boolean isRemind) {
        // 对话框ID、好友ID
        ChatItem talkBoxData = (ChatItem) talkElementPane.getUserData();
        // 对话空为空，初始化[置顶、选中、提醒]
        if (chatsListView.getItems().isEmpty()) {
            if (idxFirst) {
                chatsListView.getItems().add(0, talkElementPane);
            }
            if (selected) {
                // 设置对话框[√选中]
                chatsListView.getSelectionModel().select(talkElementPane);
            }
            isRemind(msgRemindLabel, talkType, isRemind);
            return;
        }
        // 对话空不为空，判断第一个元素是否当前聊天Pane
        Pane firstPane = chatsListView.getItems().get(0);
        // 判断元素是否在首位，如果是首位可返回不需要重新设置首位
        if (talkBoxData.getTalkId().equals(((ChatItem) firstPane.getUserData()).getTalkId())) {
            Pane selectedItem = chatsListView.getSelectionModel().getSelectedItem();
            // 选中判断；如果第一个元素已经选中[说明正在会话]，那么清空消息提醒
            if (null == selectedItem) {
                isRemind(msgRemindLabel, talkType, isRemind);
                return;
            }
            ChatItem selectedItemUserData = (ChatItem) selectedItem.getUserData();
            if (null != selectedItemUserData && talkBoxData.getTalkId().equals(selectedItemUserData.getTalkId())) {
                clearRemind(msgRemindLabel);
            } else {
                isRemind(msgRemindLabel, talkType, isRemind);
            }
            return;
        }
        if (idxFirst) {
            chatsListView.getItems().remove(talkElementPane);
            chatsListView.getItems().add(0, talkElementPane);
        }
        if (selected) {
            // 设置对话框[√选中]
            chatsListView.getSelectionModel().select(talkElementPane);
        }
        isRemind(msgRemindLabel, talkType, isRemind);
    }

    /**
     * 消息提醒
     *
     * @param msgRemindLabel 消息面板
     */
    private void isRemind(Label msgRemindLabel, int talkType, Boolean isRemind) {
        if (!isRemind) return;
        msgRemindLabel.setVisible(true);
        // 群组直接展示小红点
        if (1 == talkType) {
            return;
        }
        RemindCount remindCount = (RemindCount) msgRemindLabel.getUserData();
        // 超过10个展示省略号
        if (remindCount.getCount() > 99) {
            msgRemindLabel.setText("···");
            return;
        }
        int count = remindCount.getCount() + 1;
        msgRemindLabel.setUserData(new RemindCount(count));
        msgRemindLabel.setText(String.valueOf(count));
    }

    private void clearRemind(Label msgRemindLabel) {
        msgRemindLabel.setVisible(false);
        msgRemindLabel.setUserData(new RemindCount(0));
    }

    /**
     * 初始化当前登录用户的信息
     *
     * @param userId       用户ID
     * @param userNickName 用户昵称
     * @param userHead     头像图片名称
     */

    @Override
    public void setUserInfo(String userId, String userNickName, String userHead) {
        this.userId = userId;
        this.userNickName = userNickName;
        this.userHead = userHead;
        avatarBtn.setStyle(String.format("-fx-background-image: url('/icon/avatar/%s.jpg')", userHead));
    }

    // 聊天
    private void barChat() {
        chatsBtn.setOnAction(event -> {
            switchBarChat(chatsBtn, chatsPane, true);
            switchBarFriend(contactsBtn, contactsPane, false);
        });
        chatsBtn.setOnMouseEntered(event -> {
            boolean visible = chatsPane.isVisible();
            if (visible) return;
            chatsBtn.setStyle("-fx-background-image: url('/icon/chat/chat_click.png')");
        });
        chatsBtn.setOnMouseExited(event -> {
            boolean visible = chatsPane.isVisible();
            if (visible) return;
            chatsBtn.setStyle("-fx-background-image: url('/icon/chat/chat.png')");
        });
    }

    // 好友
    private void barFriend() {
        contactsBtn.setOnAction(event -> {
            switchBarChat(chatsBtn, chatsPane, false);
            switchBarFriend(contactsBtn, contactsPane, true);
        });
        contactsBtn.setOnMouseEntered(event -> {
            boolean visible = contactsPane.isVisible();
            if (visible) return;
            contactsBtn.setStyle("-fx-background-image: url('/icon/contact/contact_click.png')");
        });
        contactsBtn.setOnMouseExited(event -> {
            boolean visible = contactsPane.isVisible();
            if (visible) return;
            contactsBtn.setStyle("-fx-background-image: url('/icon/contact/contact.png')");
        });
    }

    @Override
    public void switchBarChats(boolean flag) {
        switchBarChat(chatsBtn, chatsPane, flag);
    }

    //
    @Override
    public void switchBarContacts(boolean flag) {
        switchBarFriend(contactsBtn, contactsPane, flag);
    }

    // 切换：chatsBtn
    private void switchBarChat(Button chatsBtn, Pane group_bar_chat, boolean toggle) {
        if (toggle) {
            chatsBtn.setStyle("-fx-background-image: url('/icon/chat/chat_selected.png')");
            group_bar_chat.setVisible(true);
        } else {
            chatsBtn.setStyle("-fx-background-image: url('/icon/chat/chat.png')");
            group_bar_chat.setVisible(false);
        }
    }

    // 切换：bar_friend
    private void switchBarFriend(Button bar_friend, Pane group_bar_friend, boolean toggle) {
        if (toggle) {
            bar_friend.setStyle("-fx-background-image: url('/icon/contact/contact_selected.png')");
            group_bar_friend.setVisible(true);
        } else {
            bar_friend.setStyle("-fx-background-image: url('/icon/contact/contact.png')");
            group_bar_friend.setVisible(false);
        }
    }

    // 好友；开启与好友发送消息 [点击发送消息时候触发 -> 添加到对话框、选中、展示对话列表]
    public void doEventOpenFriendUserSendMsg(Button sendMsgButton, String userFriendId,
                                             String userFriendNickName, String userFriendHead) {
        sendMsgButton.setOnAction(event -> {
            // 1. 添加好友到对话框
            addTalkBox(0, 0, userFriendId, userFriendNickName, userFriendHead, null, null, true);
            // 2. 切换对话框窗口
            // 切换到聊天界面
            switchBarChats(true);
            // 隐藏通讯录界面
            switchBarContacts(false);
            // TODO 3. 事件处理；填充到对话框
            presenter.doEventAddTalkUser(userId,userFriendId);
        });
    }

    // 群组；开启与群组发送消息
    public void doEventOpenFriendGroupSendMsg(Button sendMsgButton, String groupId, String groupName, String groupHead) {
        sendMsgButton.setOnAction(event -> {
            // 1. 添加好友到对话框
            addTalkBox(0, 1, groupId, groupName, groupHead, null, null, true);
            // 2. 切换对话框窗口
            // 切换到聊天界面
            switchBarChats(true);
            // 隐藏通讯录界面
            switchBarContacts(false);
            // TODO 3. 事件处理；填充到对话框
            presenter.doEventAddTalkGroup(userId, groupId);
        });
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
            presenter.doEventAddContactUser(userId,contactId);
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

    @Override
    public void doShow(LoginResponse msg) {
        setUserInfo(msg.getUserId(),msg.getNickName(),msg.getAvatar());
        super.show();
        List<ChatItemDTO> chatList = msg.getChatList();
        if (chatList != null) {
            chatList.forEach(item -> {
                addTalkBox(0, item.getChatType(), item.getChatId(), item.getNick(), item.getAvatar(), item.getMsg(), item.getDate(), true);
                List<MessagePaneDTO> messageList = item.getMessagePaneList();
                if (messageList == null || messageList.isEmpty()) return;
                messageList.sort((o1, o2) -> (int) (o1.getMsgDate().getTime() - o2.getMsgDate().getTime()));
                switch (item.getChatType()) {
                    case 0: {
                        messageList.forEach(message -> {
                            if (message.getMsgFlag() == 0)
                                addTalkMsgRight(message.getMsgPaneId()
                                        , message.getMsgBody()
                                        , message.getMsgDate()
                                        , true
                                        , false
                                        , false);
                            else if (message.getMsgFlag() == 1) {
                                addTalkMsgUserLeft(message.getMsgPaneId()
                                        , message.getMsgBody(), message.getMsgDate()
                                        , true
                                        , false
                                        , false);
                            }
                        });
                        break;
                    }
                    case 1: {
                        return;
                    }
                    default:
                        break;
                }
            });
        }
        List<ContactItemDTO> contactList = msg.getContactList();
        if (contactList != null) {
            contactList.forEach(item -> {
                addContactToContactList(false, item.getContactId()
                        , item.getContactName()
                        , item.getContactAvatar());
            });
        }

        List<GroupItemDTO> groupList = msg.getGroupList();
        if (groupList != null) {
            groupList.forEach(item -> {
                addGroupToContacts(item.getGroupId(), item.getGroupName(), item.getGroupAvatar());
            });
        }
    }

    public String getUserId() {
        return userId;
    }
}
