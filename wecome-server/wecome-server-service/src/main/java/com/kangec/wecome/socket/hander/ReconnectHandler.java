package com.kangec.wecome.socket.hander;

import com.kangec.wecome.config.ConnectionManager;
import com.kangec.wecome.service.UserService;
import com.kangec.wecome.socket.BaseHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import packet.reconnect.ReconnectRequest;

import java.util.List;

/**
 * 断线重连处理器
 *
 * @Author kangec 10/19/2020 3:10 PM
 * @Email ardien@126.com
 **/
@ChannelHandler.Sharable
public class ReconnectHandler extends BaseHandler<ReconnectRequest> {
    public ReconnectHandler(UserService userService) {
        super(userService);
    }

    @Override
    public void channelRead(Channel ctx, ReconnectRequest msg) {
        String userId = msg.getUserId();
        ConnectionManager.removeUserChannelByUserId(userId);
        ConnectionManager.put(userId, ctx);
        List<String> groupsIdList = userService.getGroupIds(msg.getUserId());
        for (String groupsId : groupsIdList) {
            ConnectionManager.putGroups(groupsId, ctx);
        }
    }
}
