package PetBase.service;

import PetBase.dao.PetDaoImpl;
import PetBase.entity.Pet;

import java.util.List;

public class PetService {
    private PetDaoImpl petDao;

    public PetService(PetDaoImpl petDao) {
        this.petDao = petDao;
    }

    public Pet addPet(Pet pet) {
        return petDao.save(pet);
    }

    public List<Pet> getAllPets() {
        return petDao.getAll();
    }
}