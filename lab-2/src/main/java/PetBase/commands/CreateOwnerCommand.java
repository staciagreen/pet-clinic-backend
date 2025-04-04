package PetBase.commands;

import PetBase.entity.Owner;
import PetBase.service.OwnerService;

import java.util.Scanner;

/**
 * Команда для создания владельца.
 */
public class CreateOwnerCommand implements Command {
    private final OwnerService ownerService;
    private final Scanner scanner;

    /**
     * @param ownerService Сервис для работы с владельцами.
     * @param scanner      Для ввода данных.
     */
    public CreateOwnerCommand(OwnerService ownerService, Scanner scanner) {
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    /**
     * Создает владельца на основе введенных данных.
     */
    @Override
    public void execute() {
        System.out.print("Введите имя владельца: ");
        String name = scanner.nextLine();
        System.out.print("Введите дату рождения владельца в формате \"дд.мм.ггг\":");
        String birthDate = scanner.nextLine();

        Owner owner = ownerService.createOwner(name, birthDate);
        System.out.println("Владелец добавлен: " + name + "(id " + owner.getId() + ")");
    }
}