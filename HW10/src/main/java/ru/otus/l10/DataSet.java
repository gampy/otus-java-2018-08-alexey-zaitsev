package ru.otus.l10;

public abstract class DataSet {
    private final long id;

    public DataSet() {
        this(0);
    }

    public DataSet(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public abstract String getName();

    public abstract int getAge();

}

