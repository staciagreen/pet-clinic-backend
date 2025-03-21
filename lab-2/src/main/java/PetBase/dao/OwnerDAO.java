package PetBase.dao;

import PetBase.entity.Owner;
import PetBase.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OwnerDAO implements GenericDAO<Owner> {

    @Override
    public Owner save(Owner owner) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(owner);
            tx.commit();
            return owner;
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Owner owner = session.get(Owner.class, id);
            if (owner != null) session.delete(owner);
            tx.commit();
        }
    }

    @Override
    public void deleteByEntity(Owner owner) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(owner);
            tx.commit();
        }
    }

    @Override
    public void deleteAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("delete from Owner").executeUpdate();
            tx.commit();
        }
    }

    @Override
    public Owner update(Owner owner) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(owner);
            tx.commit();
            return owner;
        }
    }

    @Override
    public Owner getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Owner.class, id);
        }
    }

    @Override
    public List<Owner> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Owner", Owner.class).list();
        }
    }
}
