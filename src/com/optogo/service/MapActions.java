package com.optogo.service;

import java.util.Map;

public interface MapActions<K, V> {
    Map<K,V> sortByKeysAscending(Map<K, V> map);

    Map<K,V> sortByKeysDescending(Map<K, V> map);

    Map<K,V> sortByValueAscending(Map<K, V> map);

    Map<K,V> sortByValueDescending(Map<K, V> map);
}
