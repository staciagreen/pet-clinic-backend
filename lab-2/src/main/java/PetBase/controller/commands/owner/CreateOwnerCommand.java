package PetBase.controller.commands.owner;

import PetBase.controller.commands.Command;
import PetBase.dao.entity.Owner;
import PetBase.service.OwnerService;
import PetBase.service.dto.OwnerDTO;

import java.util.Scanner;

public class CreateOwnerCommand implements Command {
    private final OwnerService ownerService;
    private final Scanner scanner;

    public CreateOwnerCommand(OwnerService ownerService, Scanner scanner) {
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите имя владельца: ");
        String name = scanner.nextLine();
        System.out.print("Введите дату рождения владельца в формате \"дд.мм.ггг\":");
        String birthDate = scanner.nextLine();

        OwnerDTO owner = ownerService.createOwner(name, birthDate);
        System.out.println("Владелец добавлен: " + owner.name() + "(id " + owner.id() + ")");
    }
}