package PetBase.parsing;

import PetBase.commands.Command;
import PetBase.commands.DeleteOwnerCommand;
import PetBase.service.OwnerService;

import java.util.Objects;
import java.util.Scanner;

/**
 * Обработчик команды "delete owner".
 * Возвращает {@link DeleteOwnerCommand}, если команда совпадает.
 */
public class DeleteOwnerCommandHandler extends CommandHandlerBase {
    private final OwnerService ownerService;
    private final Scanner scanner;

    /**
     * Создаёт обработчик команды удаления владельца.
     *
     * @param ownerService сервис для работы с владельцами
     * @param scanner      источник пользовательского ввода
     */
    public DeleteOwnerCommandHandler(OwnerService ownerService, Scanner scanner) {
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    /**
     * Обрабатывает команду "delete owner", создавая {@link DeleteOwnerCommand}.
     * Если команда не совпадает, передаёт дальше по цепочке.
     *
     * @param input строка команды
     * @return подходящая команда или результат вызова следующего обработчика
     */
    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "delete owner")) {
            return new DeleteOwnerCommand(ownerService, scanner);
        }
        return super.handle(input);
    }
}
