package packet.chat.dto;

import lombok.Builder;
import lombok.Data;
import packet.message.dto.MessagePaneDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 对话列表内的元素[0好友、1群组]
 */

@Builder
@Data
public class ChatItemDTO implements Serializable {
    private static final long serialVersionUID = 92191L;
    private String chatId;  // 对话框子元素ID
    private int chatType;   // 类型[0好友、1群组]
    private String nick;    // 昵称
    private String avatar;  // 头像
    private String msg;     // 消息
    private Date date;      // 时间
    private List<MessagePaneDTO> messagePaneList;  // 聊天记录面板
}
