package packet.message;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import packet.Command;
import packet.Packet;

import java.util.Date;

/**
 * @Author Ardien
 * @Date 10/6/2020 1:57 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class MessageResponse extends Packet {
    private String contactId;
    private String msgBody;
    private Integer msgType = 0;
    private Integer msgFlag = 0;
    private Date msgDate;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
