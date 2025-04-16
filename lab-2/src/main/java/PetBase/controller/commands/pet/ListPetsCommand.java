package PetBase.controller.commands.pet;

import PetBase.controller.commands.Command;
import PetBase.dao.entity.Pet;
import PetBase.service.PetService;
import PetBase.service.dto.PetDTO;

import java.util.List;

public class ListPetsCommand implements Command {
    private final PetService petService;

    public ListPetsCommand(PetService petService) {
        this.petService = petService;
    }

    @Override
    public void execute() {
        List<PetDTO> pets = petService.getAllPets();
        if (pets.isEmpty()) {
            System.out.println("Питомцев пока нет.");
        } else {
            System.out.println("Список питомцев:");
            for (PetDTO pet : pets) {
                System.out.printf("ID: %s | Имя: %s | Порода: %s | Цвет: %s | Хозяин: %s\n",
                        pet.id(),
                        pet.name(),
                        pet.breed(),
                        pet.color(),
                        pet.ownerName());
            }
        }
    }
}