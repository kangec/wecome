package com.kangec.client.view.ui;

import com.kangec.client.view.common.UIBinding;
import com.kangec.client.view.contract.LoginContract;
import com.kangec.client.view.contract.MainContract;
import com.kangec.client.view.presenter.LoginPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import packet.login.LoginResponse;

import java.io.IOException;
import java.util.Date;

/**
 * 登录功能视图层
 *
 * @Author Ardien
 * @Date 9/22/2020 7:14 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Getter
@Slf4j
public class LoginUI extends UIBinding implements LoginContract.View {
    private static final String RESOURCE_NAME_LOGIN_UI = "/view/login/LoginUI.fxml";
    private Parent root;                // 根节点
    private Button loginBtn;            // Login Button
    private TextField usernameTF;       // 用户名控件
    private PasswordField passwordPF;   // 密码控件
    private final LoginPresenter loginPresenter = new LoginPresenter(this);
    private MainContract.View mainView;

    public LoginUI() {
        initView();
        mainView = new MainUI();
        mainView.setUserInfo("1","1","1");
    }

    public void test() {
        mainView.setUserInfo("1","1","1");

        mainView.addTalkBox(-1, 0, "1000004", "哈尼克兔", "1", null, null, false);
        mainView.addTalkMsgUserLeft("1000004", "沉淀、分享、成长，让自己和他人都有所收获！", new Date(), true, false, true);
        mainView.addTalkMsgRight("1000004", "今年过年是放假时间最长的了！", new Date(), true, true, false);

        mainView.addTalkBox(-1, 0, "1000002", "铁锤", "4", "秋风扫过树叶落，哪有棋盘哪有我", new Date(), false);
        mainView.addTalkMsgUserLeft("1000002", "秋风扫过树叶落，哪有棋盘哪有我", new Date(), true, false, true);
        mainView.addTalkMsgRight("1000002", "我Q，传说中的老头杀？", new Date(), true, true, false);


        // 群组 - 对话框测试
        mainView.addTalkBox(0, 1, "5307397", "虫洞 · 技术栈(1区)", "1", "", new Date(), false);
        mainView.addTalkMsgRight("5307397", "你炸了我的山", new Date(), true, true, false);
        mainView.addTalkMsgGroupLeft("5307397", "1000002", "拎包冲", "12", "推我过忘川", new Date(), true, false, true);
        mainView.addTalkMsgGroupLeft("5307397", "1000003", "铁锤", "13", "奈河桥边的姑娘", new Date(), true, false, true);
        mainView.addTalkMsgGroupLeft("5307397", "1000004", "哈尼克兔", "14", "等我回头看", new Date(), true, false, true);

        mainView.addGroupToContacts("1","风花都死法","34");
        mainView.addGroupToContacts("2","风花都死法","23");
        mainView.addGroupToContacts("3","风花都死法","24");
        mainView.addGroupToContacts("4","风花都死法","25");

        mainView.addContactToContactList(false,"100002","李刚","10");
        mainView.addContactToContactList(false,"100004","李刚","11");
        mainView.addContactToContactList(false,"100005","李刚","12");
        mainView.addContactToContactList(false,"100006","李刚","13");
        mainView.addContactToContactList(false,"100007","李刚","14");
        mainView.addContactToContactList(false,"100008","李刚","15");
    }

    public void setMainView(MainContract.View mainView) {
        this.mainView = mainView;
    }

    @Override
    public void initView() {
        try {
            root = FXMLLoader.load(getClass().getResource(RESOURCE_NAME_LOGIN_UI));
            super.root = root;
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        //scene.setFill(Color.TRANSPARENT);
        setScene(scene);
        //initStyle(StageStyle.TRANSPARENT);
        setResizable(false);
        getIcons().add(new Image("/icon/icon_wecome.png"));
        setTitle("WeCome");
        loginBtn = binding("loginBtn", Button.class);
        usernameTF = binding("usernameTF", TextField.class);
        passwordPF = binding("passwordPF", PasswordField.class);
        loginBtn.setOnAction(this::doLoginAction);
    }

    private void doLoginAction(ActionEvent actionEvent) {
        String username = usernameTF.getText();
        String password = passwordPF.getText();
        if (username.length() == 0 || password.length() == 0 ) return;
        loginPresenter.login(username,password);
    }

    @Override
    public void doShow() {
        super.show();
    }

    @Override
    public void onUserNameError() {

    }

    @Override
    public void onPasswordError() {

    }

    @Override
    public LoginContract.Presenter getPresenter() {
        return loginPresenter;
    }

    /**
     * 登录事件
     *
     */
    @Override
    public void onStartLogin() {
    }

    /**
     * 登陆成功；跳转聊天窗口
     * @param msg
     */
    @Override
    public void onLoginSuccess(LoginResponse msg) {
        this.close();
        mainView.doShow(msg);
    }

    /**
     * 登录失败：弹窗提示用户
     */
    @Override
    public void onLoginFailed() {

    }
}
