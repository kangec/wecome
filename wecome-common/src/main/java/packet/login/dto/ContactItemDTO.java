package packet.login.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 通讯录列表内的联系人元素
 */

@Data
@Builder
public class ContactItemDTO implements Serializable {
    private static final long serialVersionUID = 92189L;
    private String contactId;       // 联系人标识
    private String contactName;     // 昵称
    private String contactAvatar;   // 头像
}
