package ru.otus.l11.cache;

public abstract class CachedElementImpl implements CachedElement {
    private final long creationTime;
    private long lastAccessTime;

    public CachedElementImpl()  {
        this.creationTime = this.getCurrentTime();
        this.lastAccessTime = this.getCurrentTime();
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public long getCreationTime() {
        return this.creationTime;
    }

    public long getLastAccessTime() {
        return this.lastAccessTime;
    }

    public void setAccessed() {
        this.lastAccessTime = this.getCurrentTime();
    }
}
