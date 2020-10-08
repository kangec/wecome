package packet.contact;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import packet.Command;
import packet.Packet;

/**
 * @Author Ardien
 * @Date 10/8/2020 9:51 AM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class AddContactResponse extends Packet {
    private String contactId;
    private String nickName;
    private String avatar;

    @Override
    public Byte getCommand() {
        return Command.ADD_CONTACT_RESPONSE;
    }
}
