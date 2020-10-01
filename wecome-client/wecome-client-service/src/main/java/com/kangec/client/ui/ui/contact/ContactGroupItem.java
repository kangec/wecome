package com.kangec.client.ui.ui.contact;

import com.kangec.client.ui.ui.chats.ChatGroupsData;
import com.kangec.client.ui.ui.chats.ElementIds;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * 消息群的视图结构，它将会填充在{@link ContactGroupList} 内
 */
public class ContactGroupItem {

    private Pane groupPane;

    public ContactGroupItem(String groupId, String groupName, String groupHead) {
        // 群组底板(存储群ID)
        groupPane = new Pane();
        groupPane.setId(ElementIds.ElementTalkId.createFriendGroupId(groupId));
        groupPane.setUserData(new ChatGroupsData(groupId, groupName, groupHead));
        groupPane.setPrefWidth(250);
        groupPane.setPrefHeight(70);
        groupPane.getStyleClass().add("elementFriendGroup");
        ObservableList<Node> children = groupPane.getChildren();
        // 头像区域
        Label groupHeadLabel = new Label();
        groupHeadLabel.setPrefSize(50, 50);
        groupHeadLabel.setLayoutX(15);
        groupHeadLabel.setLayoutY(10);
        groupHeadLabel.getStyleClass().add("elementFriendGroup_head");
        groupHeadLabel.setStyle(String.format("-fx-background-image: url('/icon/avatar/%s.jpg')", groupHead));
        children.add(groupHeadLabel);
        // 名称区域
        Label groupNameLabel = new Label();
        groupNameLabel.setPrefSize(200, 40);
        groupNameLabel.setLayoutX(80);
        groupNameLabel.setLayoutY(15);
        groupNameLabel.setText(groupName);
        groupNameLabel.getStyleClass().add("elementFriendGroup_name");
        children.add(groupNameLabel);
    }

    public Pane pane() {
        return groupPane;
    }
}
