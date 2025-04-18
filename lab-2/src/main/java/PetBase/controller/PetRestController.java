package PetBase.controller;

import PetBase.service.PetService;
import PetBase.service.dto.PetDTO;
import PetBase.dao.entity.Owner;
import PetBase.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetRestController {

    private final PetService petService;
    private final OwnerService ownerService;

    @Autowired
    public PetRestController(PetService petService, OwnerService ownerService) {
        this.petService = petService;
        this.ownerService = ownerService;
    }

    // Получить всех питомцев
    @GetMapping
    public List<PetDTO> getAllPets() {
        return petService.getAllPets();
    }

    // Получить одного питомца по id
    @GetMapping("/{id}")
    public PetDTO getPetById(@PathVariable Long id) {
        return PetBase.service.mapping.PetMapper.toDTO(petService.getPetById(id));
    }

    // Создать питомца
    @PostMapping
    public PetDTO createPet(@RequestParam String name,
                            @RequestParam String birthDate,
                            @RequestParam String breed,
                            @RequestParam String color,
                            @RequestParam Double tailLength,
                            @RequestParam Long ownerId) {
        Owner owner = ownerService.getOwnerById(ownerId);
        var pet = petService.createPet(name, birthDate, breed, color, owner);
        pet.setTailLength(tailLength);
        return PetBase.service.mapping.PetMapper.toDTO(pet);
    }

    // Удалить питомца
    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable Long id) {
        petService.deletePetById(id);
    }
}
