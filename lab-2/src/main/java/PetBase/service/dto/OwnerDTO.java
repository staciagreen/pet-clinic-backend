package PetBase.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Владелец питомца")
public record OwnerDTO(
        @Schema(description = "ID владельца", example = "1")
        Long id,

        @Schema(description = "Имя владельца", example = "Настя")
        String name,

        @Schema(description = "Дата рождения", example = "2002-03-15")
        String birthDate
) {}
