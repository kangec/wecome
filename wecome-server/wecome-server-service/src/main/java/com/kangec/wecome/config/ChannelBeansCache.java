package com.kangec.wecome.config;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 **/

public class ChannelBeansCache {

    private static final Map<String, Channel> userChannels = new ConcurrentHashMap<>();
    private static final Map<String, String> userChannelIds = new ConcurrentHashMap<>();
    private static final Map<String, ChannelGroup> groupChannels = new ConcurrentHashMap<>();

    public static void put(String userId, Channel channel) {
        userChannels.put(userId, channel);
        userChannelIds.put(channel.id().toString(), userId);
    }
}
