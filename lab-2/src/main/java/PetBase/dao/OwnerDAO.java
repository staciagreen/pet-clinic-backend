package PetBase.dao;

import PetBase.entity.Owner;
import PetBase.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * DAO-реализация для работы с сущностью {@link Owner}.
 * Обеспечивает CRUD-операции через Hibernate.
 */
public class OwnerDAO implements GenericDAO<Owner> {

    /**
     * Сохраняет владельца в базе данных.
     * @param owner объект владельца
     * @return сохранённый объект
     */
    @Override
    public Owner save(Owner owner) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(owner);
            tx.commit();
            return owner;
        }
    }

    /**
     * Удаляет владельца по его ID.
     * @param id идентификатор владельца
     */
    @Override
    public void deleteById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Owner owner = session.get(Owner.class, id);
            if (owner != null) session.delete(owner);
            tx.commit();
        }
    }

    /**
     * Удаляет владельца по объекту.
     * @param owner объект владельца
     */
    @Override
    public void deleteByEntity(Owner owner) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(owner);
            tx.commit();
        }
    }

    /**
     * Удаляет всех владельцев из базы данных.
     */
    @Override
    public void deleteAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("delete from Owner").executeUpdate();
            tx.commit();
        }
    }

    /**
     * Обновляет данные владельца.
     * @param owner обновлённый объект владельца
     * @return обновлённый владелец
     */
    @Override
    public Owner update(Owner owner) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(owner);
            tx.commit();
            return owner;
        }
    }

    /**
     * Получает владельца по ID.
     * @param id идентификатор владельца
     * @return объект владельца или null, если не найден
     */
    @Override
    public Owner getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Owner.class, id);
        }
    }

    /**
     * Получает список всех владельцев.
     * @return список объектов владельцев
     */
    @Override
    public List<Owner> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Owner", Owner.class).list();
        }
    }
}
