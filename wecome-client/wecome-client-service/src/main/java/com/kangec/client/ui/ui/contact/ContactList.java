package com.kangec.client.ui.ui.contact;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

/**
 * 联系人列表组件
 * 它将容纳多个 {@link ContactList} 组件
 */
public class ContactList {

    private Pane pane;
    private ListView<Pane> userListView; // 好友列表

    public ContactList(){
        pane = new Pane();
        pane.setId("friendUserList");
        pane.setPrefWidth(314);
        pane.setPrefHeight(0);// 自动计算；userListView.setPrefHeight(70 * items.size() + 10);
        pane.setLayoutX(-10);
        pane.getStyleClass().add("elementFriendUserList");
        ObservableList<Node> children = pane.getChildren();

        userListView = new ListView<>();
        userListView.setId("userListView");
        userListView.setPrefWidth(314);
        userListView.setPrefHeight(0); // 自动计算；userListView.setPrefHeight(70 * items.size() + 10);
        userListView.setLayoutX(-10);
        userListView.getStyleClass().add("elementFriendUser_listView");
        children.add(userListView);
    }

    public Pane pane() {
        return pane;
    }

}
