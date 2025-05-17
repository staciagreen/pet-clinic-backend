package PetBase.service.dto;

import java.util.List;
import java.util.Set;

public record OwnerEntityDto(
        Long id,
        String username,
        String password,
        String name,
        String birthDate,
        List<PetEntityDto> pets,
        Set<String> roles
) {}
