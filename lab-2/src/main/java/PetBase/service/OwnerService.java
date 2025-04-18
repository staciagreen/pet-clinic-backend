package PetBase.service;

import PetBase.dao.Repository.OwnerRepository;
import PetBase.dao.entity.Owner;
import PetBase.service.dto.OwnerDTO;
import PetBase.service.mapping.OwnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
