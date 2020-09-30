package com.kangec;


import com.kangec.ui.LoginUI;

import javafx.application.Application;
import javafx.stage.Stage;


public class WeComeApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        LoginUI login = new LoginUI();
        login.doShow();
    }
}
