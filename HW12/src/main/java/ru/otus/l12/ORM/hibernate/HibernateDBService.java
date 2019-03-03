package ru.otus.l12.ORM.hibernate;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.l12.ORM.DBService;
import ru.otus.l12.ORM.DataSetDAO;
import ru.otus.l12.connection.ConnectionFactory;
import ru.otus.l12.dataSets.UserDataSet;

import java.sql.SQLException;
import java.util.List;


public class HibernateDBService implements DBService<Session>  {
    private SessionFactory sessionFactory;

    public HibernateDBService()  {
        sessionFactory = ConnectionFactory.getHibernateSessionFactory("ru.otus.l12.dataSets");
    }

    @Override
    public void saveUser(UserDataSet user)  {
        user.setPassword(cryptWithMD5(user.getPassword()));
        try (Session session = getConnection()) {
            DataSetDAO<UserDataSet> dao = new HibernateDAO<>(session);
            dao.save(user);
        }
    }

    @Override
    public UserDataSet readUser(long id) throws SQLException {
        try (Session session = getConnection()) {
            DataSetDAO<UserDataSet> dao = new HibernateDAO<>(session);
            return dao.load(id, UserDataSet.class);
        }
    }

    @Override
    public UserDataSet readByLogin(String login) {
        try (Session session = getConnection()) {
            DataSetDAO<UserDataSet> dao = new HibernateDAO<>(session);
            return dao.loadByLogin(login, UserDataSet.class);
        }
    }

    @Override
    public List<UserDataSet> readAll() {
        try (Session session = getConnection()) {
            DataSetDAO<UserDataSet> dao = new HibernateDAO<>(session);
            return ((HibernateDAO<UserDataSet>) dao).loadAll(UserDataSet.class);
        }
    }


    @Override
    public Session getConnection() {
       // return (sessionFactory.getCurrentSession() == null) ? sessionFactory.openSession(): sessionFactory.getCurrentSession();
        return sessionFactory.openSession();
    }

    @Override
    public void closeConnection() {
        sessionFactory.close();
        System.out.println("\nConnection closed");
    }

    public static String cryptWithMD5(String value) {
       return DigestUtils.md5Hex(value);
    }
}