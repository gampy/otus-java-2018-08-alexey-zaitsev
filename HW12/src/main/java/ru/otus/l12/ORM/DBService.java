package ru.otus.l12.ORM;

import ru.otus.l12.dataSets.UserDataSet;
import java.sql.SQLException;
import java.util.List;

public interface DBService<ConnectionType> {

    void saveUser(UserDataSet user) throws SQLException;

    UserDataSet readUser(long id) throws SQLException;

    UserDataSet readByLogin(String login);

    List<UserDataSet> readAll();

    ConnectionType getConnection();

    void closeConnection();
}
