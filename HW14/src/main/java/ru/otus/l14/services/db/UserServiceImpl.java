package ru.otus.l14.services.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import ru.otus.l14.dao.DataSetDAO;
import ru.otus.l14.dao.HibernateDAO;
import ru.otus.l14.model.UserDataSet;
import ru.otus.l14.security.Crypt;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService<Session> {
    private SessionFactory sessionFactory;

    public UserServiceImpl(SessionFactory sessionFactory)  {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveUser(UserDataSet user)  {
        user.setPassword(Crypt.cryptWithMD5(user.getPassword()));
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
        return sessionFactory.openSession();
    }

    @Override
    public void closeConnection() {
        sessionFactory.close();
        System.out.println("\nConnection closed");
    }

}