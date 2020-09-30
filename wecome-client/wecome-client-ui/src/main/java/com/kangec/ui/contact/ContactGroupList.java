package com.kangec.ui.contact;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;


/**
 * 自定义群组组件列表：它将容纳多个 {@link ContactGroupItem} 组件
 */
public class ContactGroupList {

    private Pane pane;
    private ListView<Pane> groupListView; // 群组列表

    public ContactGroupList() {
        pane = new Pane();
        pane.setId("friendGroupList");
        pane.setPrefWidth(314);
        pane.setPrefHeight(0);// 自动计算；groupListView.setPrefHeight(70 * items.size() + 10);
        pane.setLayoutX(-10);
        pane.getStyleClass().add("elementFriendGroupList");
        ObservableList<Node> children = pane.getChildren();

        groupListView = new ListView<>();
        groupListView.setId("groupListView");
        groupListView.setPrefWidth(314);
        groupListView.setPrefHeight(0); // 自动计算；groupListView.setPrefHeight(70 * items.size() + 10);
        groupListView.setLayoutX(-10);
        groupListView.getStyleClass().add("elementFriendGroupList_listView");
        children.add(groupListView);
    }

    public Pane pane() {
        return pane;
    }

}
