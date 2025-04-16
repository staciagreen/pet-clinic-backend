package PetBase.dao;

import java.util.List;

public interface GenericDAO<T> {
    T save(T entity);

    void deleteById(Long id);

    void deleteByEntity(T entity);

    void deleteAll();

    T update(T entity);

    T getById(Long id);

    List<T> getAll();
}