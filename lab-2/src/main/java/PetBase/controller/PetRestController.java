package PetBase.controller;

import PetBase.dao.entity.Pet;
import PetBase.service.PetService;
import PetBase.service.dto.PetDTO;
import PetBase.dao.entity.Owner;
import PetBase.service.OwnerService;
import PetBase.service.mapping.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public List<PetDTO> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPetById(@PathVariable Long id) {
        Pet pet = petService.getPetById(id);
        if (pet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(PetMapper.toDTO(pet));
    }

    @PostMapping
    public ResponseEntity<PetDTO> createPet(@RequestParam String name,
                                            @RequestParam String birthDate,
                                            @RequestParam String breed,
                                            @RequestParam String color,
                                            @RequestParam Double tailLength,
                                            @RequestParam Long ownerId) {
        Owner owner = ownerService.getOwnerById(ownerId);
        if (owner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Pet pet = petService.createPet(name, birthDate, breed, color, owner);
        if (pet == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        pet.setTailLength(tailLength);
        return ResponseEntity.ok(PetMapper.toDTO(pet));
    }


    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable Long id) {
        petService.deletePetById(id);
    }

    @GetMapping("/filter")
    public Page<PetDTO> filterPets(@RequestParam(required = false) String color,
                                   @RequestParam(required = false) String breed,
                                   @RequestParam(required = false) String minDate,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return petService.filterPets(color, breed, minDate, pageable);
    }
}
