package ru.otus.l11.ORM.hibernate;

import org.hibernate.Session;
import ru.otus.l11.dataSets.DataSet;
import ru.otus.l11.ORM.DataSetDAO;

import java.sql.SQLException;

public class HibernateDAO<T extends DataSet> implements DataSetDAO<T> {
    private Session session;

    public HibernateDAO(Session session) {
        this.session = session;
    }

    @Override
    public void save(T dataSetChild) {
        session.save(dataSetChild);
    }

    @Override
    public T load(long id, Class<T> clazz) throws SQLException {
        return session.load(clazz, id);
    }
}
