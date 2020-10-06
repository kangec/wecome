package packet.message;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import packet.Command;
import packet.Packet;

import java.util.Date;

/**
 * @Author Ardien
 * @Date 10/5/2020 8:26 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class MessageRequest extends Packet {
    private String userId;
    private String contactId;
    private String msgBody;
    private int msgType = 0;            // 消息类型【0文字 1表情】
    private int msgFlag = 0;        // 消息标识【0单聊 1群聊】
    private Date msgDate;
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
