package packet;


import packet.login.LoginRequest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Ardien
 * @Date 9/29/2020 8:58 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

public abstract class Packet {

    /**
     *
     */
    private static final Map<Byte,Class<? extends Packet>> packets =
                                            new ConcurrentHashMap<Byte, Class<? extends Packet>>();

    static {
        packets.put(Command.LOGIN_REQUEST, LoginRequest.class);
    }

    /**
     *
     * @param command Identified {@link Command } of resources
     * @return extends {@link Packet} Packet
     */
    public static Class<? extends Packet> get(Byte command) {
        return packets.get(command);
    }

    /**
     * 获取指令
     * @return 指令Key
     */
    public abstract Byte getCommand();
}
