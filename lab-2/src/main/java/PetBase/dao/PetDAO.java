package PetBase.dao;

import PetBase.entity.Pet;
import PetBase.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PetDAO implements GenericDAO<Pet> {

    @Override
    public Pet save(Pet pet) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(pet);
            tx.commit();
            return pet;
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Pet pet = session.get(Pet.class, id);
            if (pet != null) session.delete(pet);
            tx.commit();
        }
    }

    @Override
    public void deleteByEntity(Pet pet) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(pet);
            tx.commit();
        }
    }

    @Override
    public void deleteAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("delete from Pet").executeUpdate();
            tx.commit();
        }
    }

    @Override
    public Pet update(Pet pet) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(pet);
            tx.commit();
            return pet;
        }
    }

    @Override
    public Pet getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Pet.class, id);
        }
    }

    @Override
    public List<Pet> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Pet", Pet.class).list();
        }
    }
}
