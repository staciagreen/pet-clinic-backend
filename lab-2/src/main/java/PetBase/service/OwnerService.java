package PetBase.service;

import PetBase.service.dto.OwnerDTO;
import PetBase.dao.OwnerDAO;
import PetBase.dao.entity.Owner;
import PetBase.service.mapping.OwnerMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {
    private OwnerDAO ownerDAO;

    public OwnerService() {
        ownerDAO = new OwnerDAO();
    }
    public OwnerService(OwnerDAO ownerDAO) {
        this.ownerDAO = ownerDAO;
    }

    public OwnerDTO createOwner(String name, String birthDate) {
        Owner owner = new Owner();
        owner.setName(name);
        owner.setBirthDate(birthDate);
        Owner saved = ownerDAO.save(owner);
        return OwnerMapper.toDto(saved);
    }


    public Owner getOwnerById(Long id) {
        return ownerDAO.getById(id); // для использования в createPet и т.д.
    }

    public void deleteOwnerById(Long id) {
        ownerDAO.deleteById(id);
    }

    public OwnerDTO updateOwner(Owner updatedOwner) {
        Owner saved = ownerDAO.update(updatedOwner);
        return OwnerMapper.toDto(saved);
    }

    public List<OwnerDTO> getAllOwners() {
        return ownerDAO.getAll()
                .stream()
                .map(OwnerMapper::toDto)
                .collect(Collectors.toList());
    }

    public OwnerDTO getOwnerDtoById(Long id) {
        Owner owner = ownerDAO.getById(id);
        return OwnerMapper.toDto(owner);
    }
}
