package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private String userId;      //当前登录用户ID
    private String contactId;   //接收方用户ID
    private MsgType msgType = MsgType.PERSONAL;    //消息类型[单聊消息（默认）/群消息]
    private MsgFlag msgFlag = MsgFlag.RECEIVE;    //收发消息标志位[接收（默认）/发送]
    private Date msgDate;       //时间
    private String msgBody;     //消息体
}
