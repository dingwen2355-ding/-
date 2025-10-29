package com.ruoyi.common.utils;

import java.util.Map;

public class MapUtils {
    public static <K, V> V getValueOrThrow(Map<K, V> map, K key) {
        V value = map.get(key);
        if (value == null) {
            throw new NullPointerException("Key not found in map: " + key);
        }
        return value;
    }
}
