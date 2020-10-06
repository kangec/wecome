package packet.chat;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import packet.Command;
import packet.Packet;

import java.util.Date;

/**
 * @Author Ardien
 * @Date 10/6/2020 7:02 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class ChatDialogResponse extends Packet {
    private String userId;
    private String contactName;
    private String contactId;
    private Integer chatType;
    private String avatar;
    private String msgBody;
    private Date msgDate;
    private boolean isSuccess;

    @Override
    public Byte getCommand() {
        return Command.CHAT_DIALOG_REQUEST_RESPONSE;
    }
}
