package com.kangec.client.ui.ui;

import com.kangec.client.ui.common.CacheUtil;
import com.kangec.client.ui.common.UIBinding;
import com.kangec.client.ui.contract.ChatContract;
import com.kangec.client.ui.presenter.ChatPresenter;
import com.kangec.client.ui.ui.chats.*;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Getter
public class ChatUI extends UIBinding implements ChatContract.View {
    private TextArea txt_input;             // 输入框
    private Label sendLabel;                // 发送按钮
    public String userId;                   // 当前用户ID
    public String userNickName;             // 当前用户昵称
    public String userHead;                 // 当前用户头像
    private ListView<Pane> chatsListView;   //会话列表;
    private ChatContract.Presenter presenter = new ChatPresenter(this);

    public ChatUI(Pane chatsPane,String userId, String userNickName, String userHead) {
        super.root = chatsPane;
        this.userId = userId;
        this.userNickName = userNickName;
        this.userHead = userHead;
        initView();
        super.move();
    }

    /**
     * 初始化视图
     */
    @Override
    public void initView() {
        viewBinding();
        sendMessageEntry();
        sendMessageMousePressed();
    }

    private void viewBinding() {
        chatsListView = binding("talkList",ListView.class);
        sendLabel= binding("touch_send", Label.class);
        txt_input = binding("txt_input", TextArea.class);
    }

    // 监听键盘Entry事件
    private void sendMessageEntry() {
        txt_input.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                doEventSendMsg();
            }
        });
    }

    // 监听发送按钮的鼠标点击事件
    private void sendMessageMousePressed() {
        sendLabel.setOnMousePressed(event -> {
            doEventSendMsg();
        });
    }

    private void doEventSendMsg() {
        MultipleSelectionModel<Pane> selectionModel = chatsListView.getSelectionModel();
        Pane selectedItem = selectionModel.getSelectedItem();
        // 对话信息
        // 不点击会话列表时 会产生空指针异常
        if (selectedItem == null) return;
        ChatItem chatItem = (ChatItem) selectedItem.getUserData();
        String msg = txt_input.getText();
        if (null == msg || "".equals(msg) || "".equals(msg.trim())) {
            return;
        }
        Date msgDate = new Date();
        // TODO 发送消息
        presenter.doSendMessage(userId,chatItem.getTalkId(),chatItem.getTalkType(),msgDate,msg);

        // 发送事件给自己添加消息
        addTalkMsgRight(chatItem.getTalkId(), msg, msgDate, true, true, false);
        txt_input.clear();
    }

    @Override
    public void doShow() {
        super.show();
    }

    @Override
    public void addTalkBox(int talkIdx, Integer talkType, String talkId, String talkName, 
                           String talkHead, String talkSketch, Date talkDate, Boolean selected) {
        // 填充到对话框
        // 判断会话框是否有该对象
        ChatContactPane elementTalk = CacheUtil.talkMap.get(talkId);
        if (null != elementTalk) {
            Node talkNode = chatsListView.lookup("#" + ElementIds.ElementTalkId.createTalkPaneId(talkId));
            if (null == talkNode) {
                chatsListView.getItems().add(talkIdx, elementTalk.pane());
                // 填充对话框消息栏
                fillInfoBox(elementTalk, talkName);
            }
            if (selected) {
                // 设置选中
                chatsListView.getSelectionModel().select(elementTalk.pane());
            }
            // 填充对话框消息栏
            fillInfoBox(elementTalk, talkName);
            return;
        }
        // 初始化对话框元素
        ChatContactPane talkElement = new ChatContactPane(talkId, talkType, talkName, talkHead,
                talkSketch, talkDate);
        CacheUtil.talkMap.put(talkId, talkElement);
        // 填充到对话框
        ObservableList<Pane> items = chatsListView.getItems();
        Pane talkElementPane = talkElement.pane();
        if (talkIdx >= 0) {
            items.add(talkIdx, talkElementPane);  // 添加到第一个位置
        } else {
            items.add(talkElementPane);           // 顺序添加
        }
        if (selected) {
            chatsListView.getSelectionModel().select(talkElementPane);
        }
        // 对话框元素点击事件
        talkElementPane.setOnMousePressed(event -> {
            // 填充消息栏
            fillInfoBox(talkElement, talkName);
            // 清除消息提醒
            Label msgRemind = talkElement.msgRemind();
            msgRemind.setUserData(new RemindCount(0));
            msgRemind.setVisible(false);
        });
        // 鼠标事件[移入/移出]
        talkElementPane.setOnMouseEntered(event -> {
            talkElement.delete().setVisible(true);
        });
        talkElementPane.setOnMouseExited(event -> {
            talkElement.delete().setVisible(false);
        });
        // 填充对话框消息栏
        fillInfoBox(talkElement, talkName);
        // 从对话框中删除
        talkElement.delete().setOnMouseClicked(event -> {
            chatsListView.getItems().remove(talkElementPane);
            binding("info_pane_box", Pane.class).getChildren().clear();
            binding("info_pane_box", Pane.class).setUserData(null);
            binding("info_name", Label.class).setText("");
            talkElement.getData().getItems().clear();
            talkElement.clearMsgSketch();

            // TODO 对话框删除
            presenter.doDeleteDialogEvent(userId, talkId);
        });
    }

    /**
     * 私有方法
     * 填充对话框消息内容
     *
     * @param talkElement 对话框元素
     * @param talkName    对话框名称
     */
    private void fillInfoBox(ChatContactPane talkElement, String talkName) {
        String talkId = talkElement.pane().getUserData().toString();
        // 填充对话列表
        Pane info_pane_box = binding("info_pane_box", Pane.class);
        String boxUserId = (String) info_pane_box.getUserData();
        // 判断是否已经填充[talkId]，当前已填充则返回
        if (talkId.equals(boxUserId)) return;
        ListView<Pane> listView = talkElement.getData();
        info_pane_box.setUserData(talkId);
        info_pane_box.getChildren().clear();
        info_pane_box.getChildren().add(listView);
        // 对话框名称
        Label info_name = binding("info_name", Label.class);
        info_name.setText(talkName);
    }

    @Override
    public void addTalkMsgUserLeft(String talkId, String msg, Date msgDate,
                                   Boolean idxFirst, Boolean selected, Boolean isRemind) {
        ChatContactPane talkElement = CacheUtil.talkMap.get(talkId);
        ListView<Pane> listView = talkElement.getData();
        ChatItemData talkUserData = (ChatItemData) listView.getUserData();
        Pane left = new MessagePane().left(talkUserData.getTalkName(), talkUserData.getTalkHead(), msg);
        // 消息填充
        listView.getItems().add(left);
        // 滚动条
        listView.scrollTo(left);
        talkElement.fillMsgSketch(msg, msgDate);
        // 设置位置&选中
        updateTalkListIdxAndSelected(0, talkElement.pane(), talkElement.msgRemind(),
                                    idxFirst, selected, isRemind);
        // 填充对话框聊天窗口
        fillInfoBox(talkElement, talkUserData.getTalkName());
    }

    @Override
    public void addTalkMsgGroupLeft(String talkId, String userId, String userNickName,
                                    String userHead, String msg, Date msgDate,
                                    Boolean idxFirst, Boolean selected, Boolean isRemind) {
        // 自己的消息抛弃
        if (this.userId.equals(userId)) return;
        ChatContactPane talkElement = CacheUtil.talkMap.get(talkId);
        if (null == talkElement) {
            ChatGroupsData groupsData = (ChatGroupsData) binding(ElementIds.ElementTalkId.createFriendGroupId(talkId), Pane.class).getUserData();
            if (null == groupsData) return;

            addTalkBox(0, 1, talkId, groupsData.getGroupName()
                      ,groupsData.getGroupHead(), userNickName + "：" + msg, msgDate, false);

            talkElement = CacheUtil.talkMap.get(talkId);
            // 事件通知(开启与群组发送消息)
            System.out.println("事件通知(开启与群组发送消息)");
        }
        ListView<Pane> listView = talkElement.getData();
        ChatItemData talkData = (ChatItemData) listView.getUserData();
        Pane left = new MessagePane().left(userNickName, userHead, msg);
        // 消息填充
        listView.getItems().add(left);
        // 滚动条
        listView.scrollTo(left);
        talkElement.fillMsgSketch(userNickName + "：" + msg, msgDate);
        // 设置位置&选中
        updateTalkListIdxAndSelected(1, talkElement.pane(), talkElement.msgRemind(), idxFirst, selected, isRemind);
        // 填充对话框聊天窗口
        fillInfoBox(talkElement, talkData.getTalkName());
    }

    @Override
    public void addTalkMsgRight(String talkId, String msg, Date msgData,
                                Boolean idxFirst, Boolean selected, Boolean isRemind) {
        ChatContactPane talkElement = CacheUtil.talkMap.get(talkId);
        ListView<Pane> listView = talkElement.getData();
        Pane right = new MessagePane().right(userNickName, userHead, msg);
        // 消息填充
        listView.getItems().add(right);
        // 滚动条
        listView.scrollTo(right);
        talkElement.fillMsgSketch(msg, msgData);
        // 设置位置&选中
        updateTalkListIdxAndSelected(0, talkElement.pane(), talkElement.msgRemind(), idxFirst, selected, isRemind);
    }

    /**
     * 更新对话框列表元素位置指定并选中[在聊天消息发送时触达]
     */
    /**
     * @param talkType        对话框类型[0好友、1群组]
     * @param talkElementPane 对话框元素面板
     * @param msgRemindLabel  消息提醒标签
     * @param idxFirst        是否设置首位
     * @param selected        是否选中
     * @param isRemind        是否提醒
     */
    void updateTalkListIdxAndSelected(int talkType, Pane talkElementPane, Label msgRemindLabel, Boolean idxFirst,
                                      Boolean selected, Boolean isRemind) {
        // 对话框ID、好友ID
        ChatItem talkBoxData = (ChatItem) talkElementPane.getUserData();
        // 对话空为空，初始化[置顶、选中、提醒]
        if (chatsListView.getItems().isEmpty()) {
            if (idxFirst) {
                chatsListView.getItems().add(0, talkElementPane);
            }
            if (selected) {
                // 设置对话框[√选中]
                chatsListView.getSelectionModel().select(talkElementPane);
            }
            isRemind(msgRemindLabel, talkType, isRemind);
            return;
        }
        // 对话空不为空，判断第一个元素是否当前聊天Pane
        Pane firstPane = chatsListView.getItems().get(0);
        // 判断元素是否在首位，如果是首位可返回不需要重新设置首位
        if (talkBoxData.getTalkId().equals(((ChatItem) firstPane.getUserData()).getTalkId())) {
            Pane selectedItem = chatsListView.getSelectionModel().getSelectedItem();
            // 选中判断；如果第一个元素已经选中[说明正在会话]，那么清空消息提醒
            if (null == selectedItem){
                isRemind(msgRemindLabel, talkType, isRemind);
                return;
            }
            ChatItem selectedItemUserData = (ChatItem) selectedItem.getUserData();
            if (null != selectedItemUserData && talkBoxData.getTalkId().equals(selectedItemUserData.getTalkId())) {
                clearRemind(msgRemindLabel);
            } else {
                isRemind(msgRemindLabel, talkType, isRemind);
            }
            return;
        }
        if (idxFirst) {
            chatsListView.getItems().remove(talkElementPane);
            chatsListView.getItems().add(0, talkElementPane);
        }
        if (selected) {
            // 设置对话框[√选中]
            chatsListView.getSelectionModel().select(talkElementPane);
        }
        isRemind(msgRemindLabel, talkType, isRemind);
    }

    /**
     * 消息提醒
     *
     * @param msgRemindLabel 消息面板
     */
    private void isRemind(Label msgRemindLabel, int talkType, Boolean isRemind) {
        if (!isRemind) return;
        msgRemindLabel.setVisible(true);
        // 群组直接展示小红点
        if (1 == talkType) {
            return;
        }
        RemindCount remindCount = (RemindCount) msgRemindLabel.getUserData();
        // 超过10个展示省略号
        if (remindCount.getCount() > 99) {
            msgRemindLabel.setText("···");
            return;
        }
        int count = remindCount.getCount() + 1;
        msgRemindLabel.setUserData(new RemindCount(count));
        msgRemindLabel.setText(String.valueOf(count));
    }

    private void clearRemind(Label msgRemindLabel) {
        msgRemindLabel.setVisible(false);
        msgRemindLabel.setUserData(new RemindCount(0));
    }
}
