package PetBase.controller.mapper;

import PetBase.controller.dto.OwnerDTO;
import PetBase.service.dto.OwnerEntityDto;

import java.util.List;
import java.util.Set;

public class OwnerDtoMapper {
    public static OwnerDTO toDto(OwnerEntityDto s) {
        return new OwnerDTO(
                s.id(),
                s.username(),
                s.name(),
                s.birthDate()
        );
    }

    public static OwnerEntityDto fromDto(OwnerDTO dto) {
        // при создании new-owner пароль передаётся отдельно
        return new OwnerEntityDto(
                dto.id(),
                dto.username(),
                null,
                dto.name(),
                dto.birthDate(),
                List.of(),  // pets загружаются сервисом
                Set.of()    // роли задаются сервисом
        );
    }
}
