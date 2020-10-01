package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 消息对象编码器
 *
 * @Author Ardien
 * @Date 9/26/2020 10:27 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public class MessageEncode extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

    }
}
