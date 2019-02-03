package ru.otus.l11.ORM.myORM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface MyDAL {

    @FunctionalInterface
    interface ExecuteHandler {
        void handle(PreparedStatement statement) throws SQLException;
    }

    @FunctionalInterface
    interface ResultHandler<T> {
        T handle(ResultSet resultSet) throws SQLException;
    }

    void execUpdate(String query, ExecuteHandler prepare);

    <T> T execQuery(String query, ExecuteHandler prepare, ResultHandler<T> handleResult) throws SQLException;

}