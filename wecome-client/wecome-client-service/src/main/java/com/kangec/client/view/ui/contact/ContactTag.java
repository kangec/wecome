package com.kangec.client.view.ui.contact;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * 自定义组件
 * 小标题视图
 */
public class ContactTag {

    private Pane pane;

    public ContactTag(String tagText){
        pane = new Pane();
        pane.setPrefSize(270, 24);
        pane.setStyle("-fx-background-color: transparent;");
        ObservableList<Node> children = pane.getChildren();

        Button label = new Button();
        label.setPrefSize(260,24);
        label.setLayoutX(5);
        label.setText(tagText);
        label.getStyleClass().add("element_friend_tag");
        children.add(label);

    }

    public Pane pane() {
        return pane;
    }

}
