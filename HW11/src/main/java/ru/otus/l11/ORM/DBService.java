package ru.otus.l11.ORM;


import ru.otus.l11.dataSets.UserDataSet;

import java.sql.SQLException;


public interface DBService<ConnectionType> {

    void saveUser(UserDataSet user) throws SQLException;

    UserDataSet readUser(long id) throws SQLException;

    ConnectionType getConnection();

    void closeConnection() throws Exception;
}
