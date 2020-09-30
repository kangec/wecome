package com.kangec.presenter;

import com.alibaba.fastjson.JSON;
import com.kangec.contract.ChatContract;
import domain.Message;
import domain.MsgFlag;
import domain.MsgType;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;


@Slf4j
public class ChatPresenter implements ChatContract.Presenter {
    private ChatContract.View view;

    public ChatContract.View getView() {
        return view;
    }

    public ChatPresenter(ChatContract.View view) {
        this.view = view;
    }

    /**
     * 向服务端发送消息
     *
     * @param userId    用户ID（自己）
     * @param contactId 好友ID
     * @param msgType   消息类型 0单人，1多人
     * @param msgDate   消息的时间
     * @param msgBody   消息体
     */
    @Override
    public void doSendMessage(String userId, String contactId, Integer msgType, Date msgDate, String msgBody) {
        log.info("消息封装 ==>> 传递给Netty客户端发送给服务器");
        Message message = Message.builder()
                .userId(userId)
                .contactId(contactId)
                .msgDate(msgDate)
                .msgFlag(MsgFlag.SEND)
                .msgType(msgType == 1 ? MsgType.GROUP : MsgType.PERSONAL)
                .msgBody(msgBody)
                .build();
        log.info(JSON.toJSONString(message));
    }

    /**
     * 事件处理；删除指定对话框 [断开与服务端的连接]
     *
     * @param userId    用户ID
     * @param contactId 对话框ID
     */
    @Override
    public void doDeleteDialogEvent(String userId, String contactId) {
        log.info("用户 " + userId +" 清除对话框 " + contactId);
    }

    //UI测试专用
    public void MockData() {
        // 好友 - 对话框测试
        view.addTalkBox(-1, 0, "1000004", "哈尼克兔", "1", null, null, false);
        view.addTalkMsgUserLeft("1000004", "沉淀、分享、成长，让自己和他人都有所收获！", new Date(), true, false, true);
        view.addTalkMsgRight("1000004", "今年过年是放假时间最长的了！", new Date(), true, true, false);

        view.addTalkBox(-1, 0, "1000002", "铁锤", "4", "秋风扫过树叶落，哪有棋盘哪有我", new Date(), false);
        view.addTalkMsgUserLeft("1000002", "秋风扫过树叶落，哪有棋盘哪有我", new Date(), true, false, true);
        view.addTalkMsgRight("1000002", "我Q，传说中的老头杀？", new Date(), true, true, false);


        // 群组 - 对话框测试
        view.addTalkBox(0, 1, "5307397", "虫洞 · 技术栈(1区)", "1", "", new Date(), false);
        view.addTalkMsgRight("5307397", "你炸了我的山", new Date(), true, true, false);
        view.addTalkMsgGroupLeft("5307397", "1000002", "拎包冲", "12", "推我过忘川", new Date(), true, false, true);
        view.addTalkMsgGroupLeft("5307397", "1000003", "铁锤", "13", "奈河桥边的姑娘", new Date(), true, false, true);
        view.addTalkMsgGroupLeft("5307397", "1000004", "哈尼克兔", "14", "等我回头看", new Date(), true, false, true);

    }
}
