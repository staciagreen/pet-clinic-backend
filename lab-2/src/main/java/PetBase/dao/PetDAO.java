package PetBase.dao;

import PetBase.dao.entity.Pet;

public class PetDAO extends AbstractGenericDAO<Pet> {
    public PetDAO() {
        super(Pet.class);
    }
}
