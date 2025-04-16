package PetBase.controller.commands.pet;

import PetBase.controller.commands.Command;
import PetBase.dao.entity.Pet;
import PetBase.service.PetService;

import java.util.Scanner;

public class DeletePetCommand implements Command {
    private final PetService petService;
    private final Scanner scanner;

    public DeletePetCommand(PetService petService, Scanner scanner) {
        this.petService = petService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID питомца для удаления: ");
        Long petId = Long.parseLong(scanner.nextLine());
        Pet pet = petService.getPetById(petId);

        if (pet == null) {
            System.out.println("Питомец не найден.");
            return;
        }

        petService.deletePetById(petId);
        System.out.println("Питомец удалён.");
    }
}