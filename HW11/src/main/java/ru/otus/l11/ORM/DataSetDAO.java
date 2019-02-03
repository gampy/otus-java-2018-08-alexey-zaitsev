package ru.otus.l11.ORM;

import ru.otus.l11.dataSets.DataSet;

import java.sql.SQLException;

public interface DataSetDAO<T extends DataSet> {

    void save(T dataSetChild);

    T load(long id, Class<T> clazz) throws SQLException;

}
