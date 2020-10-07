package packet.contact;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import packet.Command;
import packet.Packet;
import packet.contact.dto.ContactItemDTO;
import packet.contact.dto.SearchResultDTO;

import java.util.List;

/**
 * @Author Ardien
 * @Date 10/7/2020 12:55 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class SearchContactResponse extends Packet {
    List<SearchResultDTO> searchResList;

    @Override
    public Byte getCommand() {
        return Command.SEARCH_CONTACT_RESPONSE;
    }
}
