package packet.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import packet.Command;
import packet.Packet;
import packet.login.dto.ChatItemDTO;
import packet.login.dto.ContactItemDTO;
import packet.login.dto.GroupItemDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Ardien
 * @Date 9/29/2020 9:23 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

/**
 * 登录成功后的服务器反馈给客户端的数据包
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse extends Packet {
    private String userId;                    // 用户标识
    private String avatar;                    // 用户头像
    private String nickName;                  // 昵称
    private boolean idSuccess;                // 是否登录成功

    private List<ChatItemDTO> chatList = new ArrayList<ChatItemDTO>();          // 对话列表
    private List<GroupItemDTO> groupList = new ArrayList<GroupItemDTO>();       // 群组列表
    private List<ContactItemDTO> contactList = new ArrayList<ContactItemDTO>(); // 联系人列表

    /**
     * 获取指令
     *
     * @return 指令Key
     */
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
