package ru.otus.l12.ORM;

import ru.otus.l12.dataSets.DataSet;

import java.sql.SQLException;

public interface DataSetDAO<T extends DataSet> {

    void save(T dataSetChild);

    T load(long id, Class<T> clazz) throws SQLException;

    T loadByLogin(String login, Class<T> userDataSetClass);
}
