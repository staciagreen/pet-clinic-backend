package PetBase.dao;

import java.util.List;

/**
 * Общий интерфейс для DAO (Data Access Object).
 *
 * @param <T> Тип сущности.
 */
public interface GenericDAO<T> {
    /**
     * Сохраняет сущность.
     *
     * @param entity Сущность для сохранения.
     * @return Сохраненная сущность.
     */
    T save(T entity);

    /**
     * Удаляет сущность по ID.
     *
     * @param id ID сущности.
     */
    void deleteById(Long id);

    /**
     * Удаляет сущность.
     *
     * @param entity Сущность для удаления.
     */
    void deleteByEntity(T entity);

    /**
     * Удаляет все сущности.
     */
    void deleteAll();

    /**
     * Обновляет сущность.
     *
     * @param entity Сущность для обновления.
     * @return Обновленная сущность.
     */
    T update(T entity);

    /**
     * Возвращает сущность по ID.
     *
     * @param id ID сущности.
     * @return Найденная сущность.
     */
    T getById(Long id);

    /**
     * Возвращает список всех сущностей.
     *
     * @return Список сущностей.
     */
    List<T> getAll();
}