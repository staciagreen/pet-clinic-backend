package PetBase.commands;

import PetBase.commands.Command;
import PetBase.entity.Pet;
import PetBase.service.PetService;

import java.util.List;

public class ListPetsCommand implements Command {
    private final PetService petService;

    public ListPetsCommand(PetService petService) {
        this.petService = petService;
    }

    @Override
    public String getName() {
        return "Показать питомцев";
    }

    @Override
    public void execute() {
        List<Pet> pets = petService.getAllPets();
        if (pets.isEmpty()) {
            System.out.println("😿 Питомцев пока нет.");
        } else {
            System.out.println("📋 Список питомцев:");
            for (Pet pet : pets) {
                System.out.printf("ID: %d | Имя: %s | Порода: %s | Цвет: %s | Хозяин: %s\n",
                        pet.getId(),
                        pet.getName(),
                        pet.getBreed(),
                        pet.getColor(),
                        pet.getOwner().getName());
            }
        }
    }
}
