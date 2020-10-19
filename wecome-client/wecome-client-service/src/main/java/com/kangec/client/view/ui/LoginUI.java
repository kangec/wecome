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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
    private Label register;
    private final LoginPresenter loginPresenter = new LoginPresenter(this);
    private MainContract.View mainView;

    public LoginUI() {
        initView();
        mainView = new MainUI();
        mainView.setUserInfo("1","1","1");
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
        register = binding("register", Label.class);
        loginBtn.setOnAction(this::doLoginAction);
        register.setOnMousePressed(this::registerClickEvent);

    }

    private void registerClickEvent(MouseEvent mouseEvent) {
        try {
            Runtime.getRuntime().exec("cmd /c start http://localhost:8083/register");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
