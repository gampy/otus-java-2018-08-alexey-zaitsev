package ru.otus.l14.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.otus.l14.model.DataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.SQLException;
import java.util.List;

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

    @Override
    public T loadByLogin(String login, Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        Root<T> from = criteria.from(clazz);
        criteria.where(builder.equal(from.get("login"), login));
        Query<T> query = session.createQuery(criteria);
        return query.uniqueResult();
    }

    public List<T> loadAll(Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        Root<T> from =  criteria.from(clazz);
        criteria.orderBy(builder.asc(from.get("id")));
        return session.createQuery(criteria).list();
    }

}
