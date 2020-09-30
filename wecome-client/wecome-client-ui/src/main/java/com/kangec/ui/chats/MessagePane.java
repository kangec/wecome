package com.kangec.ui.chats;

import com.kangec.common.AutoSizeTool;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

/**
 * 自定义消息面板视图，用于展示消息。
 */
public class MessagePane {

    private Pane pane;

    private Pane head;              // 头像
    private Label nickName;         // 昵称区
    private Label infoContentArrow; // 内容箭头
    private TextArea infoContent;   // 内容

    /**
     * 收到的消息
     *
     * @param userNickName 用户名
     * @param userHead  用户头像
     * @param msg   消息体
     * @return  构造完成的消息展示面板
     */
    public Pane left(String userNickName, String userHead, String msg) {

        double autoHeight = AutoSizeTool.getHeight(msg);
        double autoWidth = AutoSizeTool.getWidth(msg);

        pane = new Pane();
        pane.setPrefSize(500, 50 + autoHeight);
        pane.getStyleClass().add("infoBoxElement");
        ObservableList<Node> children = pane.getChildren();

        // 头像
        head = new Pane();
        head.setPrefSize(50, 50);
        head.setLayoutX(15);
        head.setLayoutY(15);
        head.getStyleClass().add("box_head");
        head.setStyle(String.format("-fx-background-image: url('/icon/avatar/%s.jpg')", userHead));
        children.add(head);

        // 昵称
        nickName = new Label();
        nickName.setPrefSize(450, 20);
        nickName.setLayoutX(75);
        nickName.setLayoutY(5);
        nickName.setText(userNickName); // "小傅哥 | bugstack.cn"
        nickName.getStyleClass().add("box_nikeName");
        children.add(nickName);

        // 箭头
        infoContentArrow = new Label();
        infoContentArrow.setPrefSize(5, 20);
        infoContentArrow.setLayoutX(75);
        infoContentArrow.setLayoutY(30);
        infoContentArrow.getStyleClass().add("box_infoContent_arrow");
        children.add(infoContentArrow);

        // 内容
        infoContent = new TextArea();
        infoContent.setPrefWidth(autoWidth);
        infoContent.setPrefHeight(autoHeight);
        infoContent.setLayoutX(80);
        infoContent.setLayoutY(30);
        infoContent.setWrapText(true);
        infoContent.setEditable(false);
        infoContent.setText(msg);
        infoContent.getStyleClass().add("box_infoContent_left");
        children.add(infoContent);

        return pane;
    }

    /**
     * 发送的消息
     *
     * @param userNickName 用户名
     * @param userHead  用户头像
     * @param msg   消息体
     * @return  构造完成的消息展示面板
     */
    public Pane right(String userNickName, String userHead, String msg) {

        double autoHeight = AutoSizeTool.getHeight(msg);
        double autoWidth = AutoSizeTool.getWidth(msg);

        pane = new Pane();
        pane.setPrefSize(500, 50 + autoHeight);
        pane.setLayoutX(853);
        pane.setLayoutY(0);
        pane.getStyleClass().add("infoBoxElement");
        ObservableList<Node> children = pane.getChildren();

        // 头像
        head = new Pane();
        head.setPrefSize(50, 50);
        head.setLayoutX(770);
        head.setLayoutY(15);
        head.getStyleClass().add("box_head");
        head.setStyle(String.format("-fx-background-image: url('/icon/avatar/%s.jpg')", userHead));
        children.add(head);

        // 箭头
        infoContentArrow = new Label();
        infoContentArrow.setPrefSize(5, 20);
        infoContentArrow.setLayoutX(755);
        infoContentArrow.setLayoutY(15);
        infoContentArrow.getStyleClass().add("box_infoContent_arrow");
        children.add(infoContentArrow);

        // 内容
        infoContent = new TextArea();
        infoContent.setPrefWidth(autoWidth);
        infoContent.setPrefHeight(autoHeight);
        infoContent.setLayoutX(755 - autoWidth);
        infoContent.setLayoutY(15);
        infoContent.setWrapText(true);
        infoContent.setEditable(false);
        infoContent.setText(msg);
        infoContent.getStyleClass().add("box_infoContent_right");
        children.add(infoContent);

        return pane;
    }

}
