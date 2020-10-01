package com.kangec.client.ui.ui;

import com.kangec.client.ui.common.UIBinding;
import com.kangec.client.ui.contract.ChatContract;
import com.kangec.client.ui.contract.ContactsContract;
import com.kangec.client.ui.contract.MainContract;
import com.kangec.client.ui.presenter.MainPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

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
    private MainContract.Presenter mainPresenter = new MainPresenter(this);
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


    private ChatContract.View chatUI;  // 主界面持有会话界面的引用
    private ContactsContract.View contactsUI;
    private String userId;
    private String userNickName;
    private String userHead;

    public MainUI() {
        initView();
        viewBinding();
        move();
        // TODO: 主界面用户信息
        setUserInfo("13213","Kangec","10");

        // TODO: 与主界面必须信息同步
        chatUI = new ChatUI(chatsPane,userId, userNickName, userHead);
        contactsUI = new ContactsUI(contactsPane,this,chatUI);
    }

    public ChatContract.View getChatUI() {
        return chatUI;
    }

    public void setChatUI(ChatContract.View chatUI) {
        this.chatUI = chatUI;
    }

    public void setContactsUI(ContactsContract.View contactsUI) {
        this.contactsUI = contactsUI;
    }

    public ContactsContract.View getContactsUI() {
        return contactsUI;
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

        minBtn.setOnAction(event -> setIconified(true));
        closeBtn.setOnAction(event -> {
            close();
            mainPresenter.doQuitChat();
            System.exit(0);
        });

        barChat();
        barFriend();
    }

    /**
     * 初始化当前登录用户的信息
     * @param userId       用户ID
     * @param userNickName 用户昵称
     * @param userHead     头像图片名称
     */

    @Override
    public void setUserInfo(String userId, String userNickName, String userHead) {
        this.userId = userId;
        this.userNickName = userNickName;
        this.userHead = userHead;
        if (chatUI != null) chatUI.setCurrUser(userId, userNickName, userHead);
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
        switchBarChat(chatsBtn,chatsPane,flag);
    }

    //
    @Override
    public void switchBarContacts(boolean flag) {
        switchBarFriend(contactsBtn,contactsPane,flag);
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

    @Override
    public void doShow() {
        super.show();
    }

    public String getUserId() {
        return userId;
    }
}
