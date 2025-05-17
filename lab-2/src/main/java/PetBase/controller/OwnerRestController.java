package PetBase.controller;

import PetBase.controller.dto.OwnerDTO;
import PetBase.controller.mapper.OwnerDtoMapper;
import PetBase.service.OwnerService;
import PetBase.service.dto.OwnerEntityDto;
import PetBase.service.dto.PetEntityDto;
import PetBase.service.mapping.OwnerEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
@SecurityRequirement(name = "basicAuth")
public class OwnerRestController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerRestController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    @Operation(summary = "Список владельцев", description = "Доступно: ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OwnerEntityDto> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Профиль владельца", description = "Доступно: ADMIN или владелец")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(#id)")
    public ResponseEntity<OwnerEntityDto> getOwnerById(@PathVariable Long id) {
        OwnerEntityDto dto = ownerService.getOwnerDtoById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Создать владельца", description = "Доступно: ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OwnerEntityDto> createOwner(@RequestParam String name,
                                                      @RequestParam String birthDate) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        if (birthDate == null || birthDate.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        OwnerEntityDto created = ownerService.createOwner(name, birthDate);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwnerById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить владельца", description = "Доступно: ADMIN или владелец")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(#id)")
    public OwnerDTO updateOwner(@PathVariable Long id,
                                @RequestParam String username,
                                @RequestParam String name,
                                @RequestParam String birthDate) {
        OwnerEntityDto owner_old = ownerService.getOwnerById(id);
        if (owner_old == null) {
            throw new RuntimeException("Owner with id " + id + " not found");
        }
        OwnerDTO owner_new = new OwnerDTO(id, username, name, birthDate);
        OwnerEntityDto o = ownerService.updateOwner(id, OwnerDtoMapper.fromDto(owner_new));
        return OwnerDtoMapper.toDto(o);
    }


    @GetMapping("/filter")
    public Page<OwnerEntityDto> filterOwners(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String birthDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ownerService.filterOwners(name, birthDate, pageable);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        if (ex.getMessage().contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllOwners() {
        ownerService.deleteAllOwners();
    }

    @GetMapping("/{id}/pets")
    public ResponseEntity<List<PetEntityDto>> getPetsOfOwner(@PathVariable Long id) {
        if (ownerService.getOwnerById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(ownerService.getPetsOfOwner(id));
    }

}
