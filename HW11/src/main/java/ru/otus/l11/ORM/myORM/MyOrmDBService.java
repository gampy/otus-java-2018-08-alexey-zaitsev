package ru.otus.l11.ORM.myORM;

import ru.otus.l11.ORM.DBService;
import ru.otus.l11.ORM.DataSetDAO;
import ru.otus.l11.dataSets.UserDataSet;
import ru.otus.l11.cache.CacheEngine;
import ru.otus.l11.cache.CacheEngineImpl;
import ru.otus.l11.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class MyOrmDBService implements DBService<Connection> {
    private Connection session;
    private static CacheEngine<Class, ClassMetaData> cache = new CacheEngineImpl<>(100, 0, 0, false);

    public MyOrmDBService() {
        session = getConnection();
    }

    private ClassMetaData getMetaData(Class clazz) {

        ClassMetaData cmd = cache.get(clazz);

        if (cmd == null) {
            try {
                cmd = new ClassMetaData(clazz);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            cache.put(clazz, cmd);
        }
        return cmd;
    }

    public void saveUser(UserDataSet user) throws SQLException {
        try (Connection session = getConnection()) {
            DataSetDAO<UserDataSet> dao = new MyDAO<>(session, getMetaData(UserDataSet.class));
            dao.save(user);
        }
    }

    public UserDataSet readUser(long id) throws SQLException {
        try (Connection session = getConnection()) {
            DataSetDAO<UserDataSet> dao = new MyDAO<>(session, getMetaData(UserDataSet.class));
            return dao.load(id, UserDataSet.class);
        }
    }

    @Override
    public Connection getConnection() {
        return ConnectionFactory.getJDBCConnection();
    }

    @Override
    public void closeConnection() throws Exception {
        session.close();
        cache.dispose();
        System.out.println("\nConnection closed");
    }
}
