package com.kangec.client.view.ui.chats;

import com.kangec.client.view.common.DateUtil;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 自定义对话列表联系人视图结构
 * @Author Ardien
 * @Date 9/23/2020 3:24 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Getter
@Setter
public class ChatContactPane {
    private Pane pane;       // 对话面板(与好友对话、与群组对话)

    private Label head;      // 头像区域
    private Label nikeName;  // 昵称区域
    private Label msgSketch; // 信息简述
    private Label msgData;   // 信息时间
    private Label msgRemind; // 消息提醒
    private Button delete;   // 删除对话框按钮

    private ListView<Pane> data;

    public ChatContactPane(String talkId, Integer talkType, String talkName, String talkHead, String talkSketch, Date talkDate) {
        pane = new Pane();
        pane.setId(ElementIds.ElementTalkId.createTalkPaneId(talkId));
        pane.setUserData(new ChatItem(talkId, talkType, talkName, talkHead));
        pane.setPrefSize(270, 80);
        pane.getStyleClass().add("talkElement");
        ObservableList<Node> children = pane.getChildren();

        // 头像区域
        head = new Label();
        head.setPrefSize(50, 50);
        head.setLayoutX(15);
        head.setLayoutY(15);
        head.getStyleClass().add("element_head");
        head.setStyle(String.format("-fx-background-image: url('/icon/avatar/%s.jpg')", talkHead));
        children.add(head);

        // 昵称区域
        nikeName = new Label();
        nikeName.setPrefSize(140, 25);
        nikeName.setLayoutX(80);
        nikeName.setLayoutY(15);
        nikeName.setText(talkName);
        nikeName.getStyleClass().add("element_nikeName");
        children.add(nikeName);

        // 信息简述
        msgSketch = new Label();
        msgSketch.setId(ElementIds.ElementTalkId.createMsgSketchId(talkId));
        msgSketch.setPrefSize(200, 25);
        msgSketch.setLayoutX(80);
        msgSketch.setLayoutY(40);
        msgSketch.getStyleClass().add("element_msgSketch");
        children.add(msgSketch);

        // 信息时间
        msgData = new Label();
        msgData.setId(ElementIds.ElementTalkId.createMsgDataId(talkId));
        msgData.setPrefSize(60, 25);
        msgData.setLayoutX(220);
        msgData.setLayoutY(15);
        msgData.getStyleClass().add("element_msgData");
        children.add(msgData);
        // 填充；信息简述 & 信息时间
        fillMsgSketch(talkSketch, talkDate);

        // 消息提醒
        msgRemind = new Label();
        msgRemind.setPrefSize(15, 15);
        msgRemind.setLayoutX(60);
        msgRemind.setLayoutY(5);
        msgRemind.setUserData(new RemindCount());
        msgRemind.setText("");
        msgRemind.setVisible(false);
        msgRemind.getStyleClass().add("element_msgRemind");
        children.add(msgRemind);

        // 删除对话框按钮
        delete = new Button();
        delete.setVisible(false);
        delete.setPrefSize(4, 4);
        delete.setLayoutY(26);
        delete.setLayoutX(-8);
        delete.getStyleClass().add("element_delete");
        children.add(delete);

        data = new ListView<>();
        data.setId(ElementIds.ElementTalkId.createInfoBoxListId(talkId));
        data.setUserData(new ChatItemData(talkName,talkHead));
        data.setPrefSize(850, 560);
        data.getStyleClass().add("infoBoxStyle");
    }

    public Pane pane() {
        return pane;
    }

    public Button delete() {
        return delete;
    }

    public void fillMsgSketch(String talkSketch, Date talkDate) {
        if (null != talkSketch) {
            if (talkSketch.length() > 30) talkSketch = talkSketch.substring(0, 30);
            msgSketch.setText(talkSketch);
        }
        if (null == talkDate) talkDate = new Date();
        // 格式化信息
        String talkSimpleDate = DateUtil.simpleDate(talkDate);
        msgData.setText(talkSimpleDate);
    }

    public void clearMsgSketch() {
        msgSketch.setText("");
    }

    public Label msgRemind() {
        return msgRemind;
    }
}
