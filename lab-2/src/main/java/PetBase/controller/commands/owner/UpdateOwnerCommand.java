package PetBase.controller.commands.owner;

import PetBase.controller.commands.Command;
import PetBase.dao.entity.Owner;
import PetBase.service.OwnerService;

import java.util.Scanner;

public class UpdateOwnerCommand implements Command {
    private final OwnerService ownerService;
    private final Scanner scanner;

    public UpdateOwnerCommand(OwnerService ownerService, Scanner scanner) {
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID владельца для обновления: ");
        Long ownerId = Long.parseLong(scanner.nextLine());

        Owner owner = ownerService.getOwnerById(ownerId);
        if (owner == null) {
            System.out.println("Владелец не найден.");
            return;
        }

        System.out.print("Введите новое имя владельца: ");
        String name = scanner.nextLine();
        System.out.print("Введите новую дату рождения в формате \"дд.мм.ггг\": ");
        String birthDate = scanner.nextLine();

        owner.setName(name);
        owner.setBirthDate(birthDate);
        ownerService.updateOwner(owner);

        System.out.println("Владелец обновлён: " + owner.getName());
    }
}