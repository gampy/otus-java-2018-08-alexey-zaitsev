package ru.otus.l11.ORM.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.l11.ORM.DBService;
import ru.otus.l11.ORM.DataSetDAO;
import ru.otus.l11.dataSets.UserDataSet;
import ru.otus.l11.connection.ConnectionFactory;

import javax.transaction.Transactional;
import java.sql.SQLException;


public class HibernateDBService implements DBService<Session>  {
    private SessionFactory sessionFactory;

    public HibernateDBService()  {
        sessionFactory = ConnectionFactory.getHibernateSessionFactory("ru.otus.l11.dataSets");
    }

    @Override
    public void saveUser(UserDataSet user)  {
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
    public Session getConnection() {
       // return (sessionFactory.getCurrentSession() == null) ? sessionFactory.openSession(): sessionFactory.getCurrentSession();
        return sessionFactory.openSession();
    }

    @Override
    public void closeConnection() throws Exception {
        sessionFactory.close();
        System.out.println("\nConnection closed");
    }


//    public UserDataSet readByName(String name) {
//        return runInSession(session -> {
//            UserDataSetDAO dao = new UserDataSetDAO(session);
//            return dao.readByName(name);
//        });
//    }

//    public List<UserDataSet> readAll() {
//        Session session = sessionFactory.openSession();
//        UserDataSetDAO dao = new UserDataSetDAO(session);
//        return dao.readAll();
//
//    }


}