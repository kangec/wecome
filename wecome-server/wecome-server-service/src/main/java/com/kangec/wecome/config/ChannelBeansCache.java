package com.kangec.wecome.config;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 **/

public class ChannelBeansCache {

    private static final Map<String, Channel> userChannels = new ConcurrentHashMap<>();
    private static final Map<String, String> userChannelIds = new ConcurrentHashMap<>();
    private static final Map<String, ChannelGroup> groupChannels = new ConcurrentHashMap<>();

    public static Channel get(String userId) {
        return userChannels.get(userId);
    }


    public static void put(String userId, Channel channel) {
        userChannels.put(userId, channel);
        userChannelIds.put(channel.id().toString(), userId);
    }

    /**
     * 获取在线人数
     * @return 在线人数
     */
    public static Integer getUserChannelSize() {
        return userChannels.size();
    }

    /**
     * 添加群组成员列表通信管道
     *
     * @param userId      对话框ID[群号]
     * @param userChannel 群员通信管道
     */
    public synchronized static void putGroups(String userId, Channel userChannel) {
        ChannelGroup channelGroup = groupChannels.get(userId);
        if (null == channelGroup) {
            channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            groupChannels.put(userId, channelGroup);
        }
        channelGroup.add(userChannel);
    }}
