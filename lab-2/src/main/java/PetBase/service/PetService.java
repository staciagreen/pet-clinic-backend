package PetBase.service;

import PetBase.dao.PetDAO;
import PetBase.entity.Owner;
import PetBase.entity.Pet;

import java.util.List;

/**
 * Сервис для работы с питомцами.
 * Инкапсулирует бизнес-логику и обращается к {@link PetDAO}.
 */
public class PetService {
    private PetDAO petDAO = new PetDAO();

    /**
     * Создаёт нового питомца и сохраняет его в базу данных.
     *
     * @param name      имя питомца
     * @param birthDate дата рождения
     * @param breed     порода
     * @param color     цвет
     * @param owner     владелец
     * @return созданный питомец
     */
    public Pet createPet(String name, String birthDate, String breed, String color, Owner owner) {
        Pet pet = new Pet();
        pet.setName(name);
        pet.setBirthDate(birthDate);
        pet.setBreed(breed);
        pet.setColor(color);
        pet.setOwner(owner);
        return petDAO.save(pet);
    }

    /**
     * Возвращает всех питомцев из базы данных.
     *
     * @return список питомцев
     */
    public List<Pet> getAllPets() {
        return petDAO.getAll();
    }

    /**
     * Получает питомца по его ID.
     *
     * @param id идентификатор питомца
     * @return найденный питомец или {@code null}
     */
    public Pet getPetById(Long id) {
        return petDAO.getById(id);
    }

    /**
     * Удаляет питомца по его ID.
     *
     * @param id идентификатор питомца
     */
    public void deletePetById(Long id) {
        petDAO.deleteById(id);
    }

    /**
     * Обновляет данные питомца.
     *
     * @param updatedPet обновлённый объект питомца
     * @return обновлённый питомец
     */
    public Pet updatePet(Pet updatedPet) {
        return petDAO.update(updatedPet);
    }

    /**
     * Конструктор по умолчанию.
     */
    public PetService() {
        this.petDAO = new PetDAO();
    }

    /**
     * Конструктор с внедрением DAO (используется в тестах).
     *
     * @param petDAO DAO для работы с питомцами
     */
    public PetService(PetDAO petDAO) {
        this.petDAO = petDAO;
    }
}
