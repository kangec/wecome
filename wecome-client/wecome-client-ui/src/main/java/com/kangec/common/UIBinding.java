package com.kangec.common;

import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @Author Ardien
 * @Date 9/22/2020 7:17 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public abstract class UIBinding extends Stage {
    protected Parent root;
    private double xOffset;
    private double yOffset;

    /**
     * FXML属性绑定
     * @param id    控件id
     * @param clazz 控件类型
     * @param <T>   控件类型
     * @return      类型实例
     */
    public <T> T binding(String id, Class<T> clazz) {
        return (T) root.lookup("#" + id);
    }

    /**
     * 清除所有选定索引的选择
     * @param views ListView视图
     */
    public void clearViews(ListView<Pane>... views) {
        for (ListView<Pane> listView : views) {
            listView.getSelectionModel().clearSelection();
        }
    }

    /**
     * 窗体移动
     */
    public void move() {
        root.setOnMousePressed(event -> {
            xOffset = getX() - event.getScreenX();
            yOffset = getY() - event.getScreenY();
            root.setCursor(Cursor.CLOSED_HAND);
        });
        root.setOnMouseDragged(event -> {
            setX(event.getScreenX() + xOffset);
            setY(event.getScreenY() + yOffset);
        });
        root.setOnMouseReleased(event -> {
            root.setCursor(Cursor.DEFAULT);
        });
    }

    /**
     * 初始化视图
     */
    public abstract void initView();

    /**
     * 初始化事件
     */
    //public abstract void initEventDefine();
}
