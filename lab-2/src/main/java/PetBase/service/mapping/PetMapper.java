package PetBase.service.mapping;

import PetBase.dao.entity.Pet;
import PetBase.service.dto.PetDTO;

public class PetMapper {
    public static PetDTO toDTO(Pet pet) {
        return new PetDTO(
                pet.getId(),
                pet.getName(),
                pet.getBirthDate(),
                pet.getBreed(),
                pet.getColor(),
                pet.getTailLength(),
                pet.getOwner().getName()
        );
    }
}
