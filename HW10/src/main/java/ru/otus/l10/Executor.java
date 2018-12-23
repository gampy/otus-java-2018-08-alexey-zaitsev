package ru.otus.l10;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Executor {
    private final Connection connection;

    private static final String CREATE_TABLE_USER = "create table if not exists user (id bigint(20) NOT NULL auto_increment, name varchar(256), age int(3), primary key (id))";
    private static final String INSERT_USER = "insert into user(name, age) values(?, ?)";
    private static final String SELECT_USER = "select * from user where id = ?";


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

    public void prepareTables() {
        execUpdate(CREATE_TABLE_USER, statement -> {} );
        System.out.println("Table created");
    }

    public <T extends DataSet> void save(T user) {
        execUpdate(INSERT_USER, statement -> {
            statement.setString(1, user.getName());
            statement.setInt(2, user.getAge());
        });
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        return execQuery(   SELECT_USER,
                            statement -> statement.setLong(1, id),
                            result -> {
                                T user = null;
                                if (result != null) {
                                    result.next();
                                    try {
                                        user = clazz.getDeclaredConstructor(new Class[]{long.class, String.class, int.class})
                                                .newInstance(
                                                        result.getLong("id"),
                                                        result.getString("name"),
                                                        result.getInt("age")
                                                );
                                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                        e.printStackTrace();
                                    }
                                }
                                return user;
                            });
    }

    private void execUpdate(String query, ExecuteHandler prepare) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            prepare.handle(statement);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private <T> T execQuery(String query, ExecuteHandler prepare, ResultHandler<T> handleResult) throws SQLException {
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

