package PetBase.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Питомец")
public record PetDTO(
        @Schema(description = "ID питомца", example = "1")
        Long id,

        @Schema(description = "Имя питомца", example = "Барсик")
        String name,

        @Schema(description = "Дата рождения", example = "2020-05-10")
        String birthDate,

        @Schema(description = "Порода", example = "Сфинкс")
        String breed,

        @Schema(description = "Цвет", example = "белый")
        String color,

        @Schema(description = "Имя владельца", example = "Настя")
        String ownerName,

        @Schema(description = "Длина хвоста", example = "25.0")
        Double tailLength
) {}
