package PetBase.service;

import PetBase.dao.Repository.OwnerRepository;
import PetBase.dao.Repository.PetRepository;
import PetBase.dao.model.Owner;
import PetBase.dao.model.Pet;
import PetBase.service.mapping.OwnerEntityMapper;
import PetBase.service.mapping.PetEntityMapper;
import PetBase.service.dto.PetEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    public Page<PetEntityDto> getPetsPaged(Pageable pageable) {
        return petRepository.findAll(pageable)
                .map(PetEntityMapper::toEntityDto);
    }

    @Autowired
    public PetService(PetRepository petRepository, OwnerRepository ownerRepository) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }

    public PetEntityDto createPet(String name, String birthDate, String breed, String color, Owner owner) {
        Pet pet = new Pet();
        pet.setName(name);
        pet.setBirthDate(birthDate);
        pet.setBreed(breed);
        pet.setColor(color);
        pet.setOwner(owner);
        return PetEntityMapper.toEntityDto(petRepository.save(pet));
    }

    public PetEntityDto getPetById(Long id) {
        return petRepository.findById(id)
                .map(PetEntityMapper::toEntityDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pet not found with id: " + id)
                );
    }

    public void deletePetById(Long id) {
        if (!petRepository.existsById(id)) {
            throw new EntityNotFoundException("Pet not found with id: " + id);
        }
        petRepository.deleteById(id);
    }

    public List<PetEntityDto> getAllPets() {
        return petRepository.findAll().stream()
                .map(PetEntityMapper::toEntityDto)
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

    public Page<PetEntityDto> filterPets(String color, String breed, String minDate, Pageable pageable) {
        if (color != null && breed != null && minDate != null) {
            return petRepository
                    .findByColorIgnoreCaseAndBreedIgnoreCaseAndBirthDateAfter(color, breed, minDate, pageable)
                    .map(PetEntityMapper::toEntityDto);
        }
        if (color != null && breed != null) {
            return petRepository
                    .findByColorIgnoreCaseAndBreedIgnoreCase(color, breed, pageable)
                    .map(PetEntityMapper::toEntityDto);
        }
        if (color != null) {
            return petRepository
                    .findByColorIgnoreCase(color, pageable)
                    .map(PetEntityMapper::toEntityDto);
        }
        return petRepository.findAll(pageable).map(PetEntityMapper::toEntityDto);
    }

    public PetEntityDto updatePet(Long id, PetEntityDto dto) {
        // Убеждаемся, что питомец существует или бросаем 404
        var existing = getPetById(id);
        // Собираем новый DTO, сохраняя ownerId из существующего
        var merged = new PetEntityDto(
                id,
                dto.name(),
                dto.birthDate(),
                dto.breed(),
                dto.color(),
                dto.tailLength(),
                existing.ownerId()
        );
        return PetEntityMapper.toEntityDto(petRepository.save(PetEntityMapper.fromEntityDto(merged, ownerRepository.getById(dto.ownerId()))));
    }
}
