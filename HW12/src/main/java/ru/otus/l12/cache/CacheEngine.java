package ru.otus.l12.cache;

public interface CacheEngine<K, V> {

    void put(K key, V value);

    V get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();

}
