package PetBase.service.mapping;

import PetBase.dao.model.Pet;
import PetBase.dao.model.Owner;
import PetBase.service.dto.PetEntityDto;

public class PetEntityMapper {
    public static PetEntityDto toEntityDto(Pet p) {
        return new PetEntityDto(
                p.getId(),
                p.getName(),
                p.getBirthDate(),
                p.getBreed(),
                p.getColor(),
                p.getTailLength(),
                p.getOwner().getId()
        );
    }

    public static Pet fromEntityDto(PetEntityDto dto, Owner owner) {
        Pet p = new Pet();
        p.setId(dto.id());
        p.setName(dto.name());
        p.setBirthDate(dto.birthDate());
        p.setBreed(dto.breed());
        p.setColor(dto.color());
        p.setTailLength(dto.tailLength());
        p.setOwner(owner);
        return p;
    }
}
