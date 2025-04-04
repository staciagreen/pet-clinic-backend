package PetBase.commands;

import PetBase.commands.Command;
import PetBase.entity.Pet;
import PetBase.service.PetService;

import java.util.List;

/**
 * Команда для вывода списка всех питомцев.
 */
public class ListPetsCommand implements Command {
    private final PetService petService;

    /**
     * @param petService Сервис для работы с питомцами.
     */
    public ListPetsCommand(PetService petService) {
        this.petService = petService;
    }

    /**
     * Выводит список всех питомцев с их данными.
     */
    @Override
    public void execute() {
        List<Pet> pets = petService.getAllPets();
        if (pets.isEmpty()) {
            System.out.println("Питомцев пока нет.");
        } else {
            System.out.println("Список питомцев:");
            for (Pet pet : pets) {
                System.out.printf("ID: %s | Имя: %s | Порода: %s | Цвет: %s | Хозяин: %s\n",
                        pet.getId(),
                        pet.getName(),
                        pet.getBreed(),
                        pet.getColor(),
                        pet.getOwner().getName());
            }
        }
    }
}