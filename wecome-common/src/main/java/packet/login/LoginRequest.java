package packet.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import packet.Command;
import packet.Packet;

/**
 * 客户端发送给服务端的登录请求封装对象
 **/

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest extends Packet {
    private String userId;      // 用户名
    private String password;    // 密码

    /**
     * 获取指令
     * @return 指令Key
     */
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
