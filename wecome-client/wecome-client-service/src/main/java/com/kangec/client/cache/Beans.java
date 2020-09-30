package com.kangec.client.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Beans {

    public static final String CLIENT_CHANNEL = "CLIENT_CHANNEL";

    private static final Map<String, Object> cacheMap = new ConcurrentHashMap<>();

    public static synchronized void addBean(String name, Object obj) {
        cacheMap.put(name, obj);
    }

    public static <T> T getBean(String name, Class<T> t) {
        return (T) cacheMap.get(name);
    }


}
