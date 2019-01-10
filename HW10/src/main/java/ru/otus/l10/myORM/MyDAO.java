package ru.otus.l10.myORM;

import java.sql.SQLException;

public interface MyDAO<T> {

    T load(long id, Class<T> clazz) throws SQLException;

    void save(T dataSetChild);
}
