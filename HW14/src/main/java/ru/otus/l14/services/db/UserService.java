package ru.otus.l14.services.db;

import ru.otus.l14.model.UserDataSet;
import java.sql.SQLException;
import java.util.List;

public interface UserService<ConnectionType> {

    void saveUser(UserDataSet user);

    UserDataSet readUser(long id) throws SQLException;

    UserDataSet readByLogin(String login);

    List<UserDataSet> readAll();

    ConnectionType getConnection();

    void closeConnection();
}
