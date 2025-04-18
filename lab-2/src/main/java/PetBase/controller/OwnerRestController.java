package PetBase.controller;

import PetBase.service.OwnerService;
import PetBase.service.dto.OwnerDTO;
import PetBase.dao.entity.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerRestController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerRestController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    // Получить всех владельцев
    @GetMapping
    public List<OwnerDTO> getAllOwners() {
        return ownerService.getAllOwners();
    }

    // Получить одного владельца по id
    @GetMapping("/{id}")
    public OwnerDTO getOwnerById(@PathVariable Long id) {
        return ownerService.getOwnerDtoById(id);
    }

    // Создать нового владельца
    @PostMapping
    public OwnerDTO createOwner(@RequestParam String name,
                                @RequestParam String birthDate) {
        return ownerService.createOwner(name, birthDate);
    }

    // Удалить владельца
    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwnerById(id);
    }

    // Обновить владельца
    @PutMapping("/{id}")
    public OwnerDTO updateOwner(@PathVariable Long id,
                                @RequestParam String name,
                                @RequestParam String birthDate) {
        Owner owner = ownerService.getOwnerById(id);
        owner.setName(name);
        owner.setBirthDate(birthDate);
        return ownerService.updateOwner(owner);
    }
}
