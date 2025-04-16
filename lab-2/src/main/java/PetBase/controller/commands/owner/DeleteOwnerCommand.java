package PetBase.controller.commands.owner;

import PetBase.controller.commands.Command;
import PetBase.dao.entity.Owner;
import PetBase.service.OwnerService;

import java.util.Scanner;

public class DeleteOwnerCommand implements Command {
    private final OwnerService ownerService;
    private final Scanner scanner;

    public DeleteOwnerCommand(OwnerService ownerService, Scanner scanner) {
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID владельца для удаления: ");
        Long ownerId;
        try {
            ownerId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ID.");
            return;
        }

        Owner owner = ownerService.getOwnerById(ownerId);
        if (owner == null) {
            System.out.println("Владелец не найден.");
            return;
        }

        ownerService.deleteOwnerById(ownerId);
        System.out.println("Владелец удалён.");
    }
}