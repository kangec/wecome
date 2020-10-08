package com.kangec.client.view.presenter;

import com.alibaba.fastjson.JSON;
import com.kangec.client.cache.Beans;
import com.kangec.client.view.contract.MainContract;
import domain.MsgFlag;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import packet.chat.ChatDialogRequest;
import packet.contact.AddContactRequest;
import packet.contact.SearchContactRequest;
import packet.message.MessageRequest;
import utils.StatusCode;

import java.util.Date;

/**
 * @Author Ardien
 * @Date 9/26/2020 1:19 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Slf4j
public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    /**
     * 向服务端发送消息
     *
     * @param userId    用户ID（自己）
     * @param contactId 好友ID
     * @param msgFlag   消息类型 0单人，1多人
     * @param msgDate   消息的时间
     * @param msgBody   消息体
     */
    @Override
    public void doSendMessage(String userId, String contactId, Integer msgFlag, Date msgDate, String msgBody) {
        log.info("消息封装 ==>> 传递给Netty客户端发送给服务器");
        MessageRequest messageRequest = MessageRequest.builder()
                .userId(userId)
                .contactId(contactId)
                .msgFlag(msgFlag)
                .msgBody(msgBody)
                .msgDate(msgDate)
                .build();
        log.info(JSON.toJSONString(messageRequest));
        Channel channel = Beans.getBean(Beans.CLIENT_CHANNEL, Channel.class);
        channel.writeAndFlush(messageRequest);
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
        ChatDialogRequest request = ChatDialogRequest.builder()
                .action(StatusCode.Action.DELETE)
                .chatType(StatusCode.ChatType.PERSONAL.getValue())
                .contactId(contactId)
                .userId(userId)
                .build();
        Channel channel = Beans.getBean(Beans.CLIENT_CHANNEL, Channel.class);
        channel.writeAndFlush(request);
    }

    @Override
    public void doContactSearch(String userId, String text) {
        log.info("搜索好友:{}", text);
        SearchContactRequest request = SearchContactRequest.builder()
                .userId(userId)
                .key(text)
                .build();
        Channel channel = Beans.getBean(Beans.CLIENT_CHANNEL, Channel.class);
        channel.writeAndFlush(request);
    }

    @Override
    public void doEventAddTalkUser(String userId, String contactId) {
        log.info("添加对话框:{}", contactId);
        ChatDialogRequest request = ChatDialogRequest.builder()
                .action(StatusCode.Action.ADD)
                .chatType(StatusCode.ChatType.PERSONAL.getValue())
                .contactId(contactId)
                .userId(userId)
                .build();
        Channel channel = Beans.getBean(Beans.CLIENT_CHANNEL, Channel.class);
        channel.writeAndFlush(request);
    }

    @Override
    public void doEventAddTalkGroup(String userId, String groupId) {
        log.info("添加对话框:{}", groupId);
        ChatDialogRequest request = ChatDialogRequest.builder()
                .action(StatusCode.Action.ADD)
                .chatType(StatusCode.ChatType.GROUP.getValue())
                .contactId(groupId)
                .userId(userId)
                .build();
        Channel channel = Beans.getBean(Beans.CLIENT_CHANNEL, Channel.class);
        channel.writeAndFlush(request);

    }

    @Override
    public void doEventAddContactUser(String userId, String contactId) {
        log.info("添加好友:{}", contactId);
        AddContactRequest request = AddContactRequest.builder()
                .userId(userId)
                .contactId(contactId)
                .build();
        Channel channel = Beans.getBean(Beans.CLIENT_CHANNEL, Channel.class);
        channel.writeAndFlush(request);
    }

    /**
     * 本地客户端窗口退出操作
     */
    @Override
    public void doQuitChat() {
        log.info("系统退出");
        Channel channel = Beans.getBean(Beans.CLIENT_CHANNEL, Channel.class);
        channel.close();
    }
}
