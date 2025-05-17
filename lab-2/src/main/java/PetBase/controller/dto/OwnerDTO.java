package PetBase.controller.dto;

public record OwnerDTO(
        Long id,
        String username,
        String name,
        String birthDate
) {}