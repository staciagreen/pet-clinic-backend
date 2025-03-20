package PetBase.dao;

import PetBase.entity.Pet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class PetDaoImpl {
    private SessionFactory sessionFactory;

    public PetDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Pet save(Pet entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return entity;
    }

    public List<Pet> getAll() {
        Session session = sessionFactory.openSession();
        List<Pet> pets = session.createQuery("from Pet", Pet.class).list();
        session.close();
        return pets;
    }
}
