package packet;

/**
 * 指令集
 *
 * @Author Ardien
 * @Date 9/29/2020 9:01 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public final class Command {
    public final static Byte LOGIN_REQUEST = 100;
    public final static Byte LOGIN_RESPONSE = 101;
    public final static Byte MESSAGE_REQUEST = 102;
    public final static Byte MESSAGE_RESPONSE = 103;
    public final static Byte CHAT_DIALOG_REQUEST_REQUEST = 104;
    public final static Byte CHAT_DIALOG_REQUEST_RESPONSE = 105;
    public final static Byte SEARCH_CONTACT_REQUEST = 106;
    public final static Byte SEARCH_CONTACT_RESPONSE = 107;
    public final static Byte ADD_CONTACT_REQUEST = 108;
    public final static Byte ADD_CONTACT_RESPONSE = 109;
    public final static Byte RECONNECT_REQUEST = 110;
}
