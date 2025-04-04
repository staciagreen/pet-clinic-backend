package PetBase.parsing;

import PetBase.commands.UpdatePetCommand;
import PetBase.commands.Command;
import PetBase.service.OwnerService;
import PetBase.service.PetService;

import java.util.Objects;
import java.util.Scanner;

/**
 * Обработчик команды "update pet".
 * Проверяет введённую строку и, если она соответствует команде, возвращает соответствующий объект команды.
 */
public class UpdatePetCommandHandler extends CommandHandlerBase {
    private final PetService petService;
    private final OwnerService ownerService;
    private final Scanner scanner;

    /**
     * Создаёт обработчик команды обновления питомца.
     *
     * @param petService   сервис для работы с питомцами
     * @param ownerService сервис для работы с владельцами
     * @param scanner      источник пользовательского ввода
     */
    public UpdatePetCommandHandler(PetService petService, OwnerService ownerService, Scanner scanner) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    /**
     * Обрабатывает команду "update pet", создавая соответствующий объект команды.
     *
     * @param input строка, введённая пользователем
     * @return объект команды или передаёт выполнение следующему обработчику
     */
    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "update pet")) {
            return new UpdatePetCommand(petService, ownerService, scanner);
        }
        return super.handle(input);
    }
}
