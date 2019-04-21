package ru.otus.l14.dao;

import ru.otus.l14.model.DataSet;

import java.sql.SQLException;

public interface DataSetDAO<T extends DataSet> {

    void save(T dataSetChild);

    T load(long id, Class<T> clazz) throws SQLException;

    T loadByLogin(String login, Class<T> userDataSetClass);
}
