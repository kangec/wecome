package com.kangec.ui.chats;

/**
 * 对话框用户数据
 */
public class ChatItemData {

    private String talkName;
    private String talkHead;

    public ChatItemData(){}

    public ChatItemData(String talkName, String talkHead) {
        this.talkName = talkName;
        this.talkHead = talkHead;
    }

    public String getTalkName() {
        return talkName;
    }

    public void setTalkName(String talkName) {
        this.talkName = talkName;
    }

    public String getTalkHead() {
        return talkHead;
    }

    public void setTalkHead(String talkHead) {
        this.talkHead = talkHead;
    }

}
