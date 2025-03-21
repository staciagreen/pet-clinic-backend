package PetBase.commands;

import PetBase.service.OwnerService;

import java.util.Scanner;
public class CreateOwnerCommand implements Command {
    private final OwnerService ownerService;
    private final Scanner scanner;

    public CreateOwnerCommand(OwnerService ownerService, Scanner scanner) {
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    @Override
    public String getName() {
        return "Добавить владельца";
    }

    @Override
    public void execute() {
        System.out.print("Введите имя владельца: ");
        String name = scanner.nextLine();
        System.out.print("Введите дату рождения владельца: ");
        String birthDate = scanner.nextLine();

        ownerService.createOwner(name, birthDate);
        System.out.println("✅ Владелец добавлен: " + name);
    }
}
