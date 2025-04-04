package PetBase.service;

import PetBase.dao.OwnerDAO;
import PetBase.entity.Owner;

import java.util.List;

/**
 * Сервисный слой для работы с владельцами питомцев.
 * Делегирует вызовы к {@link OwnerDAO}.
 */
public class OwnerService {
    private OwnerDAO ownerDAO = new OwnerDAO();

    /**
     * Создаёт нового владельца и сохраняет его в базу данных.
     *
     * @param name      имя владельца
     * @param birthDate дата рождения владельца
     * @return созданный владелец
     */
    public Owner createOwner(String name, String birthDate) {
        Owner owner = new Owner();
        owner.setName(name);
        owner.setBirthDate(birthDate);
        return ownerDAO.save(owner);
    }

    /**
     * Возвращает всех владельцев из базы данных.
     *
     * @return список владельцев
     */
    public List<Owner> getAllOwners() {
        return ownerDAO.getAll();
    }

    /**
     * Ищет владельца по его ID.
     *
     * @param id идентификатор владельца
     * @return владелец или null, если не найден
     */
    public Owner getOwnerById(Long id) {
        return ownerDAO.getById(id);
    }

    /**
     * Удаляет владельца по его ID.
     *
     * @param id идентификатор владельца
     */
    public void deleteOwnerById(Long id) {
        ownerDAO.deleteById(id);
    }

    /**
     * Обновляет данные владельца в базе.
     *
     * @param updatedOwner владелец с обновлёнными данными
     * @return обновлённый владелец
     */
    public Owner updateOwner(Owner updatedOwner) {
        return ownerDAO.update(updatedOwner);
    }

    /**
     * Конструктор по умолчанию. Использует стандартный {@link OwnerDAO}.
     */
    public OwnerService() {
        this.ownerDAO = new OwnerDAO();
    }

    /**
     * Конструктор для подмены DAO (например, в тестах).
     *
     * @param ownerDAO объект DAO
     */
    public OwnerService(OwnerDAO ownerDAO) {
        this.ownerDAO = ownerDAO;
    }
}
