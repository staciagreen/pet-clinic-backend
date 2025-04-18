package PetBase.controller;

import PetBase.service.PetService;
import PetBase.service.dto.PetDTO;
import PetBase.dao.entity.Owner;
import PetBase.service.OwnerService;
import PetBase.service.mapping.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetRestController {
    @GetMapping("/paged")
    public Page<PetDTO> getPagedPets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return petService.getPetsPaged(pageable);
    }

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

    @GetMapping("/filter")
    public List<PetDTO> filterPets(@RequestParam(required = false) String color,
                                   @RequestParam(required = false) String breed,
                                   @RequestParam(required = false) String minDate) {
        return petService.filterPets(color, breed, minDate)
                .stream()
                .map(PetMapper::toDTO)
                .toList();
    }

}
