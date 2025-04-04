package PetBase.commands;

import PetBase.entity.Pet;
import PetBase.service.PetService;

import java.util.Scanner;

/**
 * Команда для удаления питомца.
 */
public class DeletePetCommand implements Command {
    private final PetService petService;
    private final Scanner scanner;

    /**
     * @param petService Сервис для работы с питомцами.
     * @param scanner    Для ввода данных.
     */
    public DeletePetCommand(PetService petService, Scanner scanner) {
        this.petService = petService;
        this.scanner = scanner;
    }

    /**
     * Удаляет питомца по ID.
     */
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