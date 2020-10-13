package com.kangec.wecome.socket.hander;

import com.alibaba.fastjson.JSON;
import com.kangec.wecome.config.ConnectionManager;
import com.kangec.wecome.service.UserService;
import com.kangec.wecome.socket.BaseHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import packet.contact.SearchContactRequest;
import packet.contact.SearchContactResponse;
import packet.contact.dto.SearchResultDTO;

import java.util.List;

/**
 * @Author Ardien
 * @Date 10/7/2020 1:36 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Slf4j
@ChannelHandler.Sharable
public class SearchContactHandler extends BaseHandler<SearchContactRequest> {

    public SearchContactHandler(UserService userService) {
        super(userService);
    }

    @Override
    public void channelRead(Channel channel, SearchContactRequest request) {
        log.info("好友搜索请求： {}", JSON.toJSONString(request));
        List<SearchResultDTO> searchResult = userService.searchContacts(request.getUserId(), request.getKey());
        SearchContactResponse response = SearchContactResponse.builder().searchResList(searchResult).build();
        Channel resCh = ConnectionManager.get(request.getUserId());
        resCh.writeAndFlush(response);
    }
}
