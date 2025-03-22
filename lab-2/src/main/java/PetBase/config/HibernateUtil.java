package PetBase.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Утилита для работы с Hibernate.
 * <p>
 * Создает и управляет фабрикой сессий Hibernate.
 * </p>
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Возвращает фабрику сессий Hibernate.
     *
     * @return Фабрика сессий.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Закрывает фабрику сессий.
     */
    public static void shutdown() {
        getSessionFactory().close();
    }
}