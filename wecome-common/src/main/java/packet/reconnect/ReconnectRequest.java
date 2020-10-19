package packet.reconnect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import packet.Command;
import packet.Packet;

/**
 * 断线重连请求
 *
 * @Author kangec 10/19/2020 3:07 PM
 * @Email ardien@126.com
 **/

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ReconnectRequest extends Packet {

    private String userId;

    @Override
    public Byte getCommand() {
        return Command.RECONNECT_REQUEST;
    }
}
