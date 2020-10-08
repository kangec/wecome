package packet.contact;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import packet.Command;
import packet.Packet;

/**
 * @Author Ardien
 * @Date 10/8/2020 9:49 AM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class AddContactRequest extends Packet {
    private String userId;
    private String contactId;

    @Override
    public Byte getCommand() {
        return Command.ADD_CONTACT_REQUEST;
    }
}
