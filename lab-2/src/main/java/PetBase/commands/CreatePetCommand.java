package PetBase.commands;

import PetBase.entity.Owner;
import PetBase.service.OwnerService;
import PetBase.service.PetService;

import java.util.Scanner;
import java.util.Set;

/**
 * Команда для создания питомца.
 */
public class CreatePetCommand implements Command {
    private final PetService petService;
    private final OwnerService ownerService;
    private final Scanner scanner;
    private static final Set<String> VALID_COLORS = Set.of(
            "черный", "белый", "серый", "рыжий", "коричневый"
    );

    /**
     * @param petService   Сервис для работы с питомцами.
     * @param ownerService Сервис для работы с владельцами.
     * @param scanner      Для ввода данных.
     */
    public CreatePetCommand(PetService petService, OwnerService ownerService, Scanner scanner) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    /**
     * Создает питомца на основе введенных данных.
     */
    @Override
    public void execute() {
        System.out.print("Введите имя питомца: ");
        String name = scanner.nextLine();
        System.out.print("Введите дату рождения: ");
        String birthDate = scanner.nextLine();
        System.out.print("Введите породу: ");
        String breed = scanner.nextLine();
        System.out.print("Введите цвет (черный, белый, серый, рыжий, коричневый): ");
        String color = scanner.nextLine().trim().toLowerCase();

        if (!VALID_COLORS.contains(color)) {
            System.out.println("Недопустимый цвет! Допустимые варианты: " + VALID_COLORS);
            return;
        }
        System.out.print("Введите ID владельца: ");
        Long ownerId = Long.parseLong(scanner.nextLine());
        Owner owner = ownerService.getOwnerById(ownerId);
        if (owner == null) {
            System.out.println("Владелец не найден!");
            return;
        }

        petService.createPet(name, birthDate, breed, color, owner);
        System.out.println("Питомец добавлен: " + name);
    }
}