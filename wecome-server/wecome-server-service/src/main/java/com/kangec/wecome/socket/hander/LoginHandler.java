package com.kangec.wecome.socket.hander;

import com.alibaba.fastjson.JSON;
import com.kangec.wecome.config.ChannelBeansCache;
import com.kangec.wecome.infrastructure.pojo.User;
import com.kangec.wecome.service.UserService;
import com.kangec.wecome.socket.BaseHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import packet.login.LoginRequest;
import packet.login.LoginResponse;

/**
 * 服务端登录处理器
 **/

@Component
@ChannelHandler.Sharable
@Slf4j
public class LoginHandler extends BaseHandler<LoginRequest> {
    public LoginHandler(UserService userService) {
        super(userService);
    }

    @Override
    public void channelRead(Channel ctx, LoginRequest msg) {
        log.info("用户请求登录： {}", JSON.toJSONString(msg));
        String userId = msg.getUserId();
        String password = msg.getPassword();
        boolean auth = userService.checkAuth(userId, password);

        if (!auth) {
            LoginResponse rejectLogin = LoginResponse.builder().idSuccess(false).build();
            log.info("用户登录失败，状态返回： {}", JSON.toJSONString(rejectLogin));
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
        log.info("用户登录成功，状态返回： {}", JSON.toJSONString(acceptLogin));
        ctx.writeAndFlush(acceptLogin);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error(cause.getMessage());
    }
}
