package PetBase.controller.commands.pet;

import PetBase.controller.commands.Command;
import PetBase.dao.entity.Pet;
import PetBase.service.OwnerService;
import PetBase.service.PetService;

import java.util.Scanner;

public class UpdatePetCommand implements Command {
    private final PetService petService;
    private final OwnerService ownerService;
    private final Scanner scanner;

    public UpdatePetCommand(PetService petService, OwnerService ownerService, Scanner scanner) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID питомца для обновления: ");
        Long petId;
        try {
            petId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ID.");
            return;
        }

        Pet pet = petService.getPetById(petId);
        if (pet == null) {
            System.out.println("Питомец не найден.");
            return;
        }

        System.out.print("Введите новое имя питомца (текущее: " + pet.getName() + "): ");
        pet.setName(scanner.nextLine());

        System.out.print("Введите новую дату рождения (текущая: " + pet.getBirthDate() + "): ");
        pet.setBirthDate(scanner.nextLine());

        System.out.print("Введите новую породу (текущая: " + pet.getBreed() + "): ");
        pet.setBreed(scanner.nextLine());

        System.out.print("Введите новый цвет (текущий: " + pet.getColor() + "): ");
        pet.setColor(scanner.nextLine());

        System.out.print("Введите ID нового владельца (текущий: " + pet.getOwner().getId() + "): ");
        Long ownerId = Long.parseLong(scanner.nextLine());
        var owner = ownerService.getOwnerById(ownerId);
        if (owner == null) {
            System.out.println("Владелец не найден.");
            return;
        }
        pet.setOwner(owner);

        petService.updatePet(pet);
        System.out.println("Питомец обновлён.");
    }
}