package ru.otus.l11.cache;

public interface CachedElement {

    long getCurrentTime();

    long getCreationTime();

    long getLastAccessTime();

    void setAccessed();
}
