package PetBase.commands;

import PetBase.entity.Owner;
import PetBase.entity.Pet;
import PetBase.service.OwnerService;
import PetBase.service.PetService;

import java.util.Scanner;

public class CreatePetCommand implements Command {
    private final PetService petService;
    private final OwnerService ownerService;
    private final Scanner scanner;

    public CreatePetCommand(PetService petService, OwnerService ownerService, Scanner scanner) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    @Override
    public String getName() {
        return "Добавить питомца";
    }

    @Override
    public void execute() {
        System.out.print("Введите имя питомца: ");
        String name = scanner.nextLine();
        System.out.print("Введите дату рождения: ");
        String birthDate = scanner.nextLine();
        System.out.print("Введите породу: ");
        String breed = scanner.nextLine();
        System.out.print("Введите цвет (black, white, grey и т.п.): ");
        String color = scanner.nextLine();

        System.out.print("Введите ID владельца: ");
        Long ownerId = Long.parseLong(scanner.nextLine());
        Owner owner = ownerService.getOwnerById(ownerId);
        if (owner == null) {
            System.out.println("⚠ Владелец не найден!");
            return;
        }

        petService.createPet(name, birthDate, breed, color,  owner);
        System.out.println("✅ Питомец добавлен: " + name);
    }
}
