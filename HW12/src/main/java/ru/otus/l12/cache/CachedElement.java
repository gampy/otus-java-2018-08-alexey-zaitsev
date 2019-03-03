package ru.otus.l12.cache;

public interface CachedElement<V> {

    long getCurrentTime();

    long getCreationTime();

    long getLastAccessTime();

    V getValue();

    void setValue(V value);

    void setAccessed();

}
