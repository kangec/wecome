package com.kangec.common;

import com.kangec.ui.chats.ChatContactPane;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheUtil {

    // 对话框组
    public static Map<String, ChatContactPane> talkMap = new ConcurrentHashMap<>();

}
