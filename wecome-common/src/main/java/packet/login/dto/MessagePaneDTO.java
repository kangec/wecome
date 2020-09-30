package packet.login.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 聊天窗口内的消息面板元素
 */

@Data
@Builder
public class MessagePaneDTO implements Serializable {
    private static final long serialVersionUID = 92192L;
    private String msgPaneId;       // 消息面板ID
    private String userId;          // 用户ID[自己/好友]
    private String nickName;        // 用户昵称[群组聊天]
    private String avatar;          // 用户头像[群组聊天]
    private Integer msgFlag;        // 标识[0自己/1好友]
    private String msgBody;         // 消息内容
    private Integer msgType;        // 消息类型；0文字消息、1固定表情
    private Date msgDate;           // 消息时间
}
