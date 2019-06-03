package com.optogo.service;

import java.util.Map;

public interface MapActions<K, V> {
    Map<K,V> sortAscending(Map<K, V> map);

    Map<K,V> sortDescending(Map<K, V> map);
}
