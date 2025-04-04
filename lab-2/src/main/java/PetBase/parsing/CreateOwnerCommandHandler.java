package PetBase.parsing;

import PetBase.commands.Command;
import PetBase.commands.CreateOwnerCommand;
import PetBase.service.OwnerService;

import java.util.Objects;
import java.util.Scanner;

/**
 * Обработчик команды "create owner".
 * Создаёт команду {@link CreateOwnerCommand}, если введённая строка соответствует ожидаемой.
 */
public class CreateOwnerCommandHandler extends CommandHandlerBase {
    private final OwnerService ownerService;
    private final Scanner scanner;

    /**
     * Создаёт обработчик команды создания владельца.
     *
     * @param ownerService сервис для работы с владельцами
     * @param scanner      источник пользовательского ввода
     */
    public CreateOwnerCommandHandler(OwnerService ownerService, Scanner scanner) {
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    /**
     * Обрабатывает ввод пользователя. Если команда — "create owner", создаёт и возвращает {@link CreateOwnerCommand}.
     * Иначе передаёт управление следующему обработчику в цепочке.
     *
     * @param input строка команды
     * @return команда или {@code null}, если не удалось обработать
     */
    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "create owner")) {
            return new CreateOwnerCommand(ownerService, scanner);
        }
        return super.handle(input);
    }
}
