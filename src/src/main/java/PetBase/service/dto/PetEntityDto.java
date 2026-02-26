package PetBase.service.dto;

public record PetEntityDto(
        Long id,
        String name,
        String birthDate,
        String breed,
        String color,
        Double tailLength,
        Long ownerId
) {}
