package packet.contact.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 通讯录列表内的群组元素
 */

@Data
@Builder
public class GroupItemDTO implements Serializable {
    private static final long serialVersionUID = 92190L;
    private String groupId;       // 群组人标识
    private String groupName;     // 昵称
    private String groupAvatar;   // 头像
}
