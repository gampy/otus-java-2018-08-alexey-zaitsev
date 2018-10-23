package ru.otus.l06;

import ru.otus.l111.cache.*;

import java.util.List;


public class MyAppCache<Integer, E>{

    private CacheEngine<Integer, E> cache;

    MyAppCache(int maxElements, long lifeTimeMs, long idleTimeMs) {
        cache = new CacheEngineImpl<>(maxElements, lifeTimeMs, idleTimeMs, false);
    }


     void put(List<E> references) {
        int i = 0;
        for (E element : references) {
            cache.put(new MyElement(i++, element));
        }
    }

    void print() {
        System.out.println(cache.toString());
    }

    CacheEngine<Integer, E> getCache() {
        return cache;
    }

}
