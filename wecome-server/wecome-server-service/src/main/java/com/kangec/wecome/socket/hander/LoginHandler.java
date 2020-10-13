package com.kangec.wecome.socket.hander;

import com.alibaba.fastjson.JSON;
import com.kangec.wecome.config.ConnectionManager;
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
import packet.chat.dto.ChatItemDTO;
import packet.contact.dto.ContactItemDTO;
import packet.contact.dto.GroupItemDTO;

import java.util.List;

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

        /* 登录成功后，需要完成：给客户端反馈用户信息、用户对话框列表、通讯录列表、群组列表 */

        // 将 userId 绑定 Channel，发送消息时将通过此拿到传输消息的 Channel
        ConnectionManager.put(userId,ctx);

        // 将 GroupId 与 Channel 绑定，用于收发消息。
        List<String> groupIds = userService.getGroupIds(userId);
        groupIds.forEach(groupId -> ConnectionManager.putGroups(groupId, ctx));

        // 1. 获取反馈用户信息
        User user = userService.getUserInfo(userId);

        // 2.用户的对话框列表
        List<ChatItemDTO> chatList = userService.getChatList(user.getUserId());

        // 3. 通讯录列表
        List<ContactItemDTO> contactList = userService.getContactList(user.getUserId());
        List<GroupItemDTO> groupList = userService.getGroupList(user.getUserId());
        // TODO 初始化传输数据对象
        // 添加到管道缓存中


        // 返回登录信息
        LoginResponse acceptLogin = LoginResponse.builder()
                .idSuccess(true)
                .userId(user.getUserId())
                .avatar(user.getAvatar())
                .nickName(user.getNickName())
                .chatList(chatList)
                .contactList(contactList)
                .groupList(groupList)
                .build();
        log.info("用户登录成功，状态返回： {}", JSON.toJSONString(acceptLogin));
        ctx.writeAndFlush(acceptLogin);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage());
    }
}
