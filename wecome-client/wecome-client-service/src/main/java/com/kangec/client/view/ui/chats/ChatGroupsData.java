package com.kangec.client.view.ui.chats;

/**
 * 群组数据(ElementFriendGroup)
 */
public class ChatGroupsData {

    private String groupId;    // 群组ID
    private String groupName;  // 群组名称
    private String groupHead;  // 群组头像

    public ChatGroupsData() {
    }

    public ChatGroupsData(String groupId, String groupName, String groupHead) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupHead = groupHead;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupHead() {
        return groupHead;
    }

    public void setGroupHead(String groupHead) {
        this.groupHead = groupHead;
    }
}
