package ru.otus.l10;

public abstract class DataSet {
    private long id;

    public DataSet() {
    }

    public DataSet(long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public abstract String getName();

    public abstract int getAge();

}

