package PetBase.commands;

import PetBase.entity.Owner;
import PetBase.service.OwnerService;

import java.util.Scanner;

/**
 * Команда для обновления данных владельца.
 */
public class UpdateOwnerCommand implements Command {
    private final OwnerService ownerService;
    private final Scanner scanner;

    /**
     * @param ownerService Сервис для работы с владельцами.
     * @param scanner      Для ввода данных.
     */
    public UpdateOwnerCommand(OwnerService ownerService, Scanner scanner) {
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    /**
     * Обновляет данные владельца по ID.
     */
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