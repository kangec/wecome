package packet.contact.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Ardien
 * @Date 10/7/2020 1:03 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Builder
@Data
public class SearchResultDTO implements Serializable {
    private static final long serialVersionUID = 921100L;
    private String contactId;       // 联系人标识
    private String contactName;     // 昵称
    private String contactAvatar;   // 头像
    private Integer status;
}
