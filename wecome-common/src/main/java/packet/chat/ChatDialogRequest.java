package packet.chat;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import packet.Command;
import packet.Packet;
import utils.StatusCode;

/**
 * @Author Ardien
 * @Date 10/6/2020 6:55 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class ChatDialogRequest extends Packet {
    private String userId;
    private String contactId;
    private Integer chatType;
    private StatusCode.Action action;

    @Override
    public Byte getCommand() {
        return Command.CHAT_DIALOG_REQUEST_REQUEST;
    }
}
