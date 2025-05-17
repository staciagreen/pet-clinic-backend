package PetBase.controller.dto;

public record PetDTO(
        Long id,
        String name,
        String birthDate,
        String breed,
        String color,
        Double tailLength,
        Long ownerId
) {
}
