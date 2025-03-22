package PetBase.parsing;

import PetBase.commands.Command;
import PetBase.commands.CreatePetCommand;
import PetBase.service.OwnerService;
import PetBase.service.PetService;

import java.util.Objects;
import java.util.Scanner;

/**
 * Обработчик команды "create pet".
 * Создаёт команду {@link CreatePetCommand}, если введённая строка соответствует ожидаемой.
 */
public class CreatePetCommandHandler extends CommandHandlerBase {
    private final PetService petService;
    private final OwnerService ownerService;
    private final Scanner scanner;

    /**
     * Создаёт обработчик команды создания питомца.
     *
     * @param petService   сервис для работы с питомцами
     * @param ownerService сервис для работы с владельцами
     * @param scanner      источник пользовательского ввода
     */
    public CreatePetCommandHandler(PetService petService, OwnerService ownerService, Scanner scanner) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    /**
     * Обрабатывает ввод пользователя. Если команда — "create pet", создаёт и возвращает {@link CreatePetCommand}.
     * Иначе передаёт управление следующему обработчику в цепочке.
     *
     * @param input строка команды
     * @return команда или {@code null}, если не удалось обработать
     */
    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "create pet")) {
            return new CreatePetCommand(petService, ownerService, scanner);
        }
        return super.handle(input);
    }
}
