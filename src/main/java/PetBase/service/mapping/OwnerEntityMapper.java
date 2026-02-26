package PetBase.service.mapping;

import PetBase.dao.model.Owner;
import PetBase.service.dto.OwnerEntityDto;
import java.util.stream.Collectors;

public class OwnerEntityMapper {
    public static OwnerEntityDto toEntityDto(Owner o) {
        return new OwnerEntityDto(
                o.getId(),
                o.getUsername(),
                o.getPassword(),
                o.getName(),
                o.getBirthDate(),
                o.getPets().stream()
                        .map(PetEntityMapper::toEntityDto)
                        .collect(Collectors.toList()),
                o.getRoles()
        );
    }


    public static Owner fromEntityDTO(OwnerEntityDto dto) {
        Owner o = new Owner();
        o.setId(dto.id());
        o.setUsername(dto.username());
        o.setPassword(dto.password());
        o.setName(dto.name());
        o.setBirthDate(dto.birthDate());
        o.setRoles(dto.roles());
        return o;
    }
}
