package ru.otus.l11.ORM.myORM;

import java.sql.*;

public class Executor implements MyDAL{
    private final Connection connection;


    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void execUpdate(String query, ExecuteHandler prepare) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            prepare.handle(statement);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> T execQuery(String query, ExecuteHandler prepare, ResultHandler<T> handleResult) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            prepare.handle(statement);
            statement.execute();
            ResultSet result = statement.getResultSet();
            return handleResult.handle(result);
        }
    }

    Connection getConnection() {
        return connection;
    }
}

