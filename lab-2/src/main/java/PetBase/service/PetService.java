package PetBase.service;

import PetBase.dao.Repository.PetRepository;
import PetBase.dao.entity.Owner;
import PetBase.dao.entity.Pet;
import PetBase.service.mapping.PetMapper;
import PetBase.service.dto.PetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public Page<PetDTO> getPetsPaged(Pageable pageable) {
        return petRepository.findAll(pageable)
                .map(PetMapper::toDTO);
    }

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet createPet(String name, String birthDate, String breed, String color, Owner owner) {
        Pet pet = new Pet();
        pet.setName(name);
        pet.setBirthDate(birthDate);
        pet.setBreed(breed);
        pet.setColor(color);
        pet.setOwner(owner);
        return petRepository.save(pet);
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    public void deletePetById(Long id) {
        petRepository.deleteById(id);
    }

    public List<PetDTO> getAllPets() {
        return petRepository.findAll().stream()
                .map(PetMapper::toDTO)
                .toList();
    }

    public List<Pet> filterPets(String color, String breed, String minDate) {
        if (color != null && breed != null && minDate != null) {
            return petRepository.findByColorIgnoreCaseAndBreedIgnoreCaseAndBirthDateAfter(color, breed, minDate);
        }
        if (color != null && breed != null) {
            return petRepository.findByColorIgnoreCaseAndBreedIgnoreCase(color, breed);
        }
        if (color != null) {
            return petRepository.findByColorIgnoreCase(color);
        }
        return petRepository.findAll();
    }

    public Page<PetDTO> filterPets(String color, String breed, String minDate, Pageable pageable) {
        if (color != null && breed != null && minDate != null) {
            return petRepository
                    .findByColorIgnoreCaseAndBreedIgnoreCaseAndBirthDateAfter(color, breed, minDate, pageable)
                    .map(PetMapper::toDTO);
        }
        if (color != null && breed != null) {
            return petRepository
                    .findByColorIgnoreCaseAndBreedIgnoreCase(color, breed, pageable)
                    .map(PetMapper::toDTO);
        }
        if (color != null) {
            return petRepository
                    .findByColorIgnoreCase(color, pageable)
                    .map(PetMapper::toDTO);
        }
        return petRepository.findAll(pageable).map(PetMapper::toDTO);
    }
}
