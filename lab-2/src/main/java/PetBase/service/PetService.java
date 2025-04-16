package PetBase.service;

import PetBase.dao.PetDAO;
import PetBase.dao.entity.Owner;
import PetBase.dao.entity.Pet;
import PetBase.service.dto.OwnerDTO;
import PetBase.service.dto.PetDTO;
import PetBase.service.mapping.OwnerMapper;
import PetBase.service.mapping.PetMapper;

import java.util.List;
import java.util.stream.Collectors;

public class PetService {
    private PetDAO petDAO;

    public PetService() {
        petDAO = new PetDAO();
    }

    public PetService(PetDAO petDAO) {
        this.petDAO = petDAO;
    }

    public Pet createPet(String name, String birthDate, String breed, String color, Owner owner) {
        Pet pet = new Pet();
        pet.setName(name);
        pet.setBirthDate(birthDate);
        pet.setBreed(breed);
        pet.setColor(color);
        pet.setOwner(owner);
        return petDAO.save(pet);
    }

    public Pet getPetById(Long id) {
        return petDAO.getById(id);
    }

    public void deletePetById(Long id) {
        petDAO.deleteById(id);
    }

    public void deletePetByEntity(Pet pet) {
        petDAO.deleteByEntity(pet);
    }

    public Pet updatePet(Pet updatedPet) {
        return petDAO.update(updatedPet);
    }

    public void addFriendship(Long petId, Long friendId) {
        Pet pet = petDAO.getById(petId);
        Pet friend = petDAO.getById(friendId);
        if (pet == null || friend == null) return;
        if (!pet.getFriends().contains(friend)) {
            pet.getFriends().add(friend);
            friend.getFriends().add(pet);
        }
        petDAO.update(pet);
        petDAO.update(friend);
    }

    public List<PetDTO> getAllPets() {
        return petDAO.getAll()
                .stream()
                .map(PetMapper::toDTO)
                .collect(Collectors.toList());
    }
}
