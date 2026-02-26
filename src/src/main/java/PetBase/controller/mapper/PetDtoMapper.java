package PetBase.controller.mapper;

import PetBase.controller.dto.PetDTO;
import PetBase.service.dto.PetEntityDto;

public class PetDtoMapper {
    public static PetDTO fromService(PetEntityDto s) {
        return new PetDTO(
                s.id(),
                s.name(),
                s.birthDate(),
                s.breed(),
                s.color(),
                s.tailLength(),
                s.ownerId()
        );
    }

    public static PetEntityDto toService(PetDTO dto) {
        return new PetEntityDto(
                dto.id(),
                dto.name(),
                dto.birthDate(),
                dto.breed(),
                dto.color(),
                dto.tailLength(),
                dto.ownerId()
        );
    }
}
