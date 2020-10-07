package com.kangec.client.socket.handler;

import com.alibaba.fastjson.JSON;
import com.kangec.client.service.UIService;
import com.kangec.client.socket.BaseHandler;
import com.kangec.client.view.contract.MainContract;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import packet.contact.SearchContactResponse;
import packet.contact.dto.ContactItemDTO;
import packet.contact.dto.SearchResultDTO;

import java.util.List;

/**
 * @Author Ardien
 * @Date 10/7/2020 12:58 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Slf4j
@ChannelHandler.Sharable
public class SearchContactHandler extends BaseHandler<SearchContactResponse> {
    public SearchContactHandler(UIService uiService) {
        super(uiService);
    }

    @Override
    public void channelRead(Channel channel, SearchContactResponse response) {
        List<SearchResultDTO> resList = response.getSearchResList();
        if (resList == null || resList.isEmpty()) return;
        log.info("渲染搜索结果视图： {}", JSON.toJSONString(resList));
        MainContract.View mainView = uiService.getMainView();
        Platform.runLater(() -> {
            resList.forEach(res -> {
                mainView.addLuckFriend(res.getContactId(), res.getContactName(), res.getContactAvatar(), res.getStatus());
            });
        });
    }
}
