package com.faith.javabasic.lru;

import java.util.LinkedHashMap;
import java.util.Map;

public class LurCache<K, V> {

    private LinkedHashMap map;

    public LurCache(int capacity) {
        map = new LinkedHashMap(0, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return map.size() > capacity;
            }
        };
    }

    public void put(K key, V value) {
        map.put(key, value);
    }

    public V get(K key) {
        return (V) map.get(key);
    }

    public void remove(K key) {
        map.remove(key);
    }

    public boolean contain(K key) {
        return map.containsKey(key);
    }
}
