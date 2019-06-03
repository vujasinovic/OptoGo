package com.optogo.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapUtil {
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static <V, K> Map<K, V> reverse(Map<K, V> map) {
        Map<K, V> reverse = new LinkedHashMap<>();

        List<K> keys = new ArrayList<>(map.keySet());
        for (int i = keys.size() - 1; i >= 0; i--) {
            K key = keys.get(i);
            reverse.put(key, map.get(key));
        }
        return reverse;
    }

}