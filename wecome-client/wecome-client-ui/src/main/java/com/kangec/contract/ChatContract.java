package com.kangec.contract;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.util.Date;

/**
 * Chats界面MVP协议
 *
 * @Author Ardien
 * @Date 9/22/2020 11:34 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public interface ChatContract {

    interface Presenter {

        /**
         * 向服务端发送消息
         * @param userId 用户ID（自己）
         * @param contactId 好友ID
         * @param msgType   消息类型 0单人，1多人
         * @param msgDate   消息的时间
         * @param msgBody   消息体
         */
        void doSendMessage(String userId, String contactId, Integer msgType, Date msgDate, String msgBody);

        /**
         * 事件处理；删除指定对话框 [断开与服务端的连接]
         *
         * @param userId 用户ID
         * @param contactId 对话框ID
         */
        void doDeleteDialogEvent(String userId, String contactId);

    }

    interface View {
        void doShow();

        /**
         * 填充对话框消息-好友[别人的消息]
         *
         * @param talkId   对话框ID[用户ID]
         * @param msg      消息
         * @param msgData  时间
         * @param idxFirst 是否设置首位
         * @param selected 是否选中
         * @param isRemind 是否提醒
         */
        void addTalkMsgUserLeft(String talkId, String msg, Date msgData,
                                Boolean idxFirst, Boolean selected, Boolean isRemind);

        /**
         * 填充对话框消息-群组[别人的消息]
         *
         * @param talkId       对话框ID[群组ID]
         * @param userId       用户ID[群员]
         * @param userNickName 用户昵称
         * @param userHead     用户头像
         * @param msg          消息
         * @param msgDate      时间
         * @param idxFirst     是否设置首位
         * @param selected     是否选中
         * @param isRemind     是否提醒
         */
        void addTalkMsgGroupLeft(String talkId, String userId, String userNickName,
                                 String userHead, String msg, Date msgDate,
                                 Boolean idxFirst, Boolean selected, Boolean isRemind);

        /**
         * 填充对话框消息[自己的消息]
         *
         * @param talkId   对话框ID[用户ID]
         * @param msg      消息
         * @param msgData  时间
         * @param idxFirst 是否设置首位
         * @param selected 是否选中
         * @param isRemind 是否提醒
         */
        void addTalkMsgRight(String talkId, String msg, Date msgData,
                             Boolean idxFirst, Boolean selected, Boolean isRemind);


        /**
         * 填充对话框列表
         *
         * @param talkIdx    对话框位置；首位0、默认-1
         * @param talkType   对话框类型；好友0、群组1
         * @param talkId     对话框ID，1v1聊天ID、1vn聊天ID
         * @param talkName   对话框名称
         * @param talkHead   对话框头像
         * @param talkSketch 对话框通信简述(聊天内容最后一组信息)
         * @param talkDate   对话框通信时间
         * @param selected   选中[true/false]
         */
        void addTalkBox(int talkIdx,
                          Integer talkType,
                          String talkId,
                          String talkName,
                          String talkHead,
                          String talkSketch,
                          Date talkDate,
                          Boolean selected);
    }
}
