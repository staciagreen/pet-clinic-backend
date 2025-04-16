package PetBase.dao;

import PetBase.dao.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public abstract class AbstractGenericDAO<T> implements GenericDAO<T> {
    private final Class<T> clazz;

    public AbstractGenericDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T save(T entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            return entity;
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            T entity = session.get(clazz, id);
            if (entity != null) session.delete(entity);
            tx.commit();
        }
    }

    @Override
    public void deleteByEntity(T entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();
        }
    }

    @Override
    public void deleteAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("delete from " + clazz.getSimpleName()).executeUpdate();
            tx.commit();
        }
    }

    @Override
    public T update(T entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            return entity;
        }
    }

    @Override
    public T getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(clazz, id);
        }
    }

    @Override
    public List<T> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from " + clazz.getSimpleName(), clazz).list();
        }
    }
}
