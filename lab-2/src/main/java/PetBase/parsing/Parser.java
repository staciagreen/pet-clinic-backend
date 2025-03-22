package PetBase.parsing;

import PetBase.commands.Command;
import PetBase.service.OwnerService;
import PetBase.service.PetService;

import java.util.Scanner;

/**
 * Класс {@code Parser} отвечает за парсинг строк команд, введённых пользователем,
 * и создание соответствующих объектов-команд с помощью цепочки обработчиков (Chain of Responsibility).
 */
public class Parser {
    private final CommandHandler root;

    /**
     * Конструктор создаёт цепочку обработчиков команд (хендлеров),
     * связывая между собой все доступные команды.
     *
     * @param ownerService сервис для работы с владельцами
     * @param petService   сервис для работы с питомцами
     * @param scanner      источник пользовательского ввода
     */
    public Parser(OwnerService ownerService, PetService petService, Scanner scanner) {
        CommandHandler listOwners = new ListOwnersCommandHandler(ownerService);
        CommandHandler createOwner = new CreateOwnerCommandHandler(ownerService, scanner);
        CommandHandler updateOwner = new UpdateOwnerCommandHandler(ownerService, scanner);
        CommandHandler deleteOwner = new DeleteOwnerCommandHandler(ownerService, scanner);

        CommandHandler listPets = new ListPetsCommandHandler(petService);
        CommandHandler createPet = new CreatePetCommandHandler(petService, ownerService, scanner);
        CommandHandler updatePet = new UpdatePetCommandHandler(petService, ownerService, scanner);
        CommandHandler deletePet = new DeletePetCommandHandler(petService, scanner);

        // Настройка последовательной цепочки обработки команд
        listOwners.setNext(createOwner)
                .setNext(updateOwner)
                .setNext(deleteOwner)
                .setNext(listPets)
                .setNext(createPet)
                .setNext(updatePet)
                .setNext(deletePet);

        this.root = listOwners;
    }

    /**
     * Выполняет парсинг входной строки и возвращает соответствующую команду.
     *
     * @param input строка, введённая пользователем
     * @return объект команды, соответствующий строке, либо {@code null}, если ни один обработчик не подошёл
     */
    public Command parse(String input) {
        return root.handle(input);
    }
}
