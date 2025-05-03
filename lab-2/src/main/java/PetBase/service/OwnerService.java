package PetBase.service;

import PetBase.dao.Repository.OwnerRepository;
import PetBase.dao.entity.Owner;
import PetBase.service.dto.OwnerDTO;
import PetBase.service.dto.PetDTO;
import PetBase.service.mapping.OwnerMapper;
import PetBase.service.mapping.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public OwnerDTO createOwner(String name, String birthDate) {
        Owner owner = new Owner();
        owner.setName(name);
        owner.setBirthDate(birthDate);
        return OwnerMapper.toDto(ownerRepository.save(owner));
    }

    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id).orElse(null);
    }

    public void deleteOwnerById(Long id) {
        ownerRepository.deleteById(id);
    }

    public OwnerDTO updateOwner(Owner updatedOwner) {
        return OwnerMapper.toDto(ownerRepository.save(updatedOwner));
    }

    public List<OwnerDTO> getAllOwners() {
        return ownerRepository.findAll().stream()
                .map(OwnerMapper::toDto)
                .toList();
    }

    public OwnerDTO getOwnerDtoById(Long id) {
        return ownerRepository.findById(id).map(OwnerMapper::toDto).orElse(null);
    }

    public Page<OwnerDTO> filterOwners(String name, String birthDate, Pageable pageable) {
        if (name != null && birthDate != null) {
            return ownerRepository
                    .findByNameIgnoreCaseContainingAndBirthDateContaining(name, birthDate, pageable)
                    .map(OwnerMapper::toDto);
        }
        if (name != null) {
            return ownerRepository
                    .findByNameIgnoreCaseContaining(name, pageable)
                    .map(OwnerMapper::toDto);
        }
        if (birthDate != null) {
            return ownerRepository
                    .findByBirthDateContaining(birthDate, pageable)
                    .map(OwnerMapper::toDto);
        }
        return ownerRepository.findAll(pageable).map(OwnerMapper::toDto);
    }

    @Autowired
    private PetService petService;

    public List<PetDTO> getPetsByOwnerId(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).orElse(null);
        if (owner == null) {
            return null;
        }
        return owner.getPets().stream()
                .map(PetMapper::toDTO)
                .toList();
    }

    public void deleteAllOwners() {
        ownerRepository.deleteAll();
    }

    public List<PetDTO> getPetsOfOwner(Long ownerId) {
        return petService.getAllPets().stream()
                .filter(pet -> {
                    Owner owner = petService.getPetById(pet.id()).getOwner();
                    return owner != null && owner.getId().equals(ownerId);
                })
                .toList();
    }

}
