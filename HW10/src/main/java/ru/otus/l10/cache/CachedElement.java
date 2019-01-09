package ru.otus.l10.cache;

public interface CachedElement {

    long getCurrentTime();

    long getCreationTime();

    long getLastAccessTime();

    void setAccessed();
}
