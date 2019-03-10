package ru.otus.l12.cache;

import java.util.Map;

public interface CacheEngine<K, V> {

    void put(K key, V value);

    V get(K key);

    Map<K, V> getAll();

    int getHitCount();

    int getMissCount();

    void dispose();

}
