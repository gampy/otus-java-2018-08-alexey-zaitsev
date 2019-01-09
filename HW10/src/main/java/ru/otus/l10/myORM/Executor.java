package ru.otus.l10.myORM;

import java.sql.*;

public class Executor {
    private final Connection connection;

    @FunctionalInterface
    interface ExecuteHandler {
        void handle(PreparedStatement statement) throws SQLException;
    }

    @FunctionalInterface
    interface ResultHandler<T> {
        T handle(ResultSet resultSet) throws SQLException;
    }

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

