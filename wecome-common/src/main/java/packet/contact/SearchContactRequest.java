package packet.contact;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import packet.Command;
import packet.Packet;

/**
 * @Author Ardien
 * @Date 10/7/2020 12:51 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class SearchContactRequest extends Packet {
    private String userId;
    private String key;

    @Override
    public Byte getCommand() {
        return Command.SEARCH_CONTACT_REQUEST;
    }
}
