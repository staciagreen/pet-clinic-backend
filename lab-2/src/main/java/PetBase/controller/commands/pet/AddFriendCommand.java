package PetBase.controller.commands.pet;

import PetBase.controller.commands.Command;
import PetBase.service.PetService;

import java.util.Scanner;

public class AddFriendCommand implements Command {
    private final PetService petService;
    private final Scanner scanner;

    public AddFriendCommand(PetService petService, Scanner scanner) {
        this.petService = petService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID питомца: ");
        Long petId = Long.parseLong(scanner.nextLine());

        System.out.print("Введите ID друга: ");
        Long friendId = Long.parseLong(scanner.nextLine());

        petService.addFriendship(petId, friendId);
        System.out.println("Дружба добавлена 🐾");
    }
}
