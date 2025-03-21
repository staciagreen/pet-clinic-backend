package PetBase.service;

import PetBase.dao.PetDAO;
import PetBase.entity.Owner;
import PetBase.entity.Pet;

import java.util.List;

public class PetService {
    private PetDAO petDAO = new PetDAO();

    public Pet createPet(String name, String birthDate, String breed, String color, Owner owner) {
        Pet pet = new Pet();
        pet.setName(name);
        pet.setBirthDate(birthDate);
        pet.setBreed(breed);
        pet.setColor(color);
        pet.setOwner(owner);
        return petDAO.save(pet);
    }

    public List<Pet> getAllPets() {
        return petDAO.getAll();
    }

    public Pet getPetById(Long id) {
        return petDAO.getById(id);
    }

    public void deletePetById(Long id) {
        petDAO.deleteById(id);
    }

    public Pet updatePet(Pet updatedPet) {
        return petDAO.update(updatedPet);
    }

    public PetService() {
        this.petDAO = new PetDAO();
    }

    // для тестов
    public PetService(PetDAO petDAO) {
        this.petDAO = petDAO;
    }
}
