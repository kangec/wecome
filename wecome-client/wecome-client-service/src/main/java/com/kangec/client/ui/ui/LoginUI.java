package com.kangec.client.ui.ui;

import com.kangec.client.ui.common.UIBinding;
import com.kangec.client.ui.contract.LoginContract;
import com.kangec.client.ui.contract.MainContract;
import com.kangec.client.ui.presenter.LoginPresenter;
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

import java.io.IOException;

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
        if (mainView == null) {
            mainView = new MainUI();
        }
    }

    /**
     * 登陆成功；跳转聊天窗口
     */
    @Override
    public void onLoginSuccess() {
        this.close();
        mainView.doShow();
    }

    /**
     * 登录失败：弹窗提示用户
     */
    @Override
    public void onLoginFailed() {

    }
}
