package PetBase.controller;

import PetBase.service.OwnerService;
import PetBase.service.dto.OwnerDTO;
import PetBase.dao.entity.Owner;
import PetBase.service.dto.PetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerRestController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerRestController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public List<OwnerDTO> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> getOwnerById(@PathVariable Long id) {
        OwnerDTO dto = ownerService.getOwnerDtoById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<OwnerDTO> createOwner(@RequestParam String name,
                                                @RequestParam String birthDate) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        if (birthDate == null || birthDate.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        OwnerDTO created = ownerService.createOwner(name, birthDate);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwnerById(id);
    }

    @PutMapping("/{id}")
    public OwnerDTO updateOwner(@PathVariable Long id,
                                @RequestParam String name,
                                @RequestParam String birthDate) {
        Owner owner = ownerService.getOwnerById(id);
        if (owner == null) {
            throw new RuntimeException("Owner with id " + id + " not found");
        }
        owner.setName(name);
        owner.setBirthDate(birthDate);
        return ownerService.updateOwner(owner);
    }


    @GetMapping("/filter")
    public Page<OwnerDTO> filterOwners(
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
    public ResponseEntity<List<PetDTO>> getPetsOfOwner(@PathVariable Long id) {
        if (ownerService.getOwnerById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(ownerService.getPetsOfOwner(id));
    }

}
