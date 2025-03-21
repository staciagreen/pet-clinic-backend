package PetBase.service;
import PetBase.dao.OwnerDAO;
import PetBase.entity.Owner;

import java.util.List;

public class OwnerService {
    private OwnerDAO ownerDAO = new OwnerDAO();

    public Owner createOwner(String name, String birthDate) {
        Owner owner = new Owner();
        owner.setName(name);
        owner.setBirthDate(birthDate);
        return ownerDAO.save(owner);
    }

    public List<Owner> getAllOwners() {
        return ownerDAO.getAll();
    }

    public Owner getOwnerById(Long id) {
        return ownerDAO.getById(id);
    }

    public void deleteOwnerById(Long id) {
        ownerDAO.deleteById(id);
    }

    public Owner updateOwner(Owner updatedOwner) {
        return ownerDAO.update(updatedOwner);
    }

    public OwnerService() {
        this.ownerDAO = new OwnerDAO();
    }

    public OwnerService(OwnerDAO ownerDAO) {
        this.ownerDAO = ownerDAO;
    }

}
