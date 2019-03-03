package ru.otus.l12.cache;

public class CachedElementImpl<V> implements CachedElement<V> {
    private final long creationTime;
    private long lastAccessTime;
    private V value;

    public CachedElementImpl()  {
        this(null);
    }

    public CachedElementImpl(V value)  {
        this.creationTime = this.getCurrentTime();
        this.lastAccessTime = this.getCurrentTime();
        this.value = value;
    }


    @Override
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    @Override
    public long getCreationTime() {
        return this.creationTime;
    }

    @Override
    public long getLastAccessTime() {
        return this.lastAccessTime;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public void setAccessed() {
        this.lastAccessTime = this.getCurrentTime();
    }
}
