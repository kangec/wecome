package com.kangec.wecome.socket.hander;

import com.kangec.wecome.config.ChannelBeansCache;
import com.kangec.wecome.infrastructure.pojo.User;
import com.kangec.wecome.service.UserService;
import com.kangec.wecome.socket.BaseHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import org.springframework.stereotype.Component;
import packet.login.LoginRequest;
import packet.login.LoginResponse;

/**
 * 服务端登录处理器
 **/

@Component
@ChannelHandler.Sharable
public class LoginHandler extends BaseHandler<LoginRequest> {
    public LoginHandler(UserService userService) {
        super(userService);
    }

    @Override
    public void channelRead(Channel ctx, LoginRequest msg) {
        String userId = msg.getUserId();
        String password = msg.getPassword();
        boolean auth = userService.checkAuth(userId, password);

        if (!auth) {
            LoginResponse rejectLogin = LoginResponse.builder().idSuccess(false).build();
            ctx.writeAndFlush(rejectLogin);
            return;
        }

        // TODO 查询数据
        User user = userService.queryUserInfo(userId);
        // TODO 初始化传输数据对象
        // 添加到管道缓存中
        ChannelBeansCache.put(userId,ctx);

        // 返回登录信息
        LoginResponse acceptLogin = LoginResponse.builder()
                .idSuccess(true)
                .userId(user.getUserId())
                .avatar(user.getAvatar())
                .nickName(user.getNickName())
                .chatList(null)
                .contactList(null)
                .groupList(null)
                .build();

        ctx.writeAndFlush(acceptLogin);
    }
}
