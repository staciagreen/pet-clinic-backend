package PetBase.service.mapping;

import PetBase.dao.entity.Owner;
import PetBase.service.dto.OwnerDTO;

public class OwnerMapper {
    public static OwnerDTO toDto(Owner owner) {
        return new OwnerDTO(
                owner.getId(),
                owner.getName(),
                owner.getBirthDate()
        );
    }
}
