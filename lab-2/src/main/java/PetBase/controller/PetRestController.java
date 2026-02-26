package PetBase.controller;

import PetBase.dao.model.Pet;
import PetBase.exception.ResourceNotFoundException;
import PetBase.service.PetService;
import PetBase.service.dto.OwnerEntityDto;
import PetBase.service.dto.PetEntityDto;
import PetBase.dao.model.Owner;
import PetBase.service.OwnerService;
import PetBase.service.mapping.OwnerEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@SecurityRequirement(name = "basicAuth")
public class PetRestController {

    @GetMapping("/paged")
    public Page<PetEntityDto> getPagedPets(
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
    @Operation(summary = "Список всех питомцев", description = "Доступно: USER, ADMIN")
    public List<PetEntityDto> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Детали питомца", description = "Доступно: USER, ADMIN")
    public ResponseEntity<PetEntityDto> getPetById(@PathVariable Long id) {
        PetEntityDto dto = petService.getPetById(id);
        if (dto == null) {
            throw new ResourceNotFoundException("Pet", "id", id);
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Создать питомца", description = "Доступно: ADMIN или владелец")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(#ownerId)")
    public ResponseEntity<PetEntityDto> createPet(@RequestParam String name,
                                                  @RequestParam String birthDate,
                                                  @RequestParam String breed,
                                                  @RequestParam String color,
                                                  @RequestParam Double tailLength,
                                                  @RequestParam Long ownerId) {
        OwnerEntityDto owner = ownerService.getOwnerById(ownerId);
        if (owner == null) {
            throw new ResourceNotFoundException("Owner", "id", ownerId);
        }

        PetEntityDto created = petService.createPet(name, birthDate, breed, color, OwnerEntityMapper.fromEntityDTO(owner));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить питомца", description = "Доступно: ADMIN или владелец")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isPetOwner(#id)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable Long id) {
        petService.deletePetById(id);
    }

    @GetMapping("/filter")
    public Page<PetEntityDto> filterPets(@RequestParam(required = false) String color,
                                         @RequestParam(required = false) String breed,
                                         @RequestParam(required = false) String minDate,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return petService.filterPets(color, breed, minDate, pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить питомца", description = "Доступно: ADMIN или владелец")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isPetOwner(#id)")
    public ResponseEntity<PetEntityDto> updatePet(
            @PathVariable Long id,
            @RequestBody PetEntityDto dto) {

        PetEntityDto updated = petService.updatePet(id, dto);
        return ResponseEntity.ok(updated);
    }
}
