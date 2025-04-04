package PetBase.parsing;

import PetBase.commands.UpdateOwnerCommand;
import PetBase.commands.Command;
import PetBase.service.OwnerService;

import java.util.Objects;
import java.util.Scanner;

/**
 * Обработчик команды "update owner".
 * Проверяет введённую строку и, если она соответствует команде, возвращает соответствующий объект команды.
 */
public class UpdateOwnerCommandHandler extends CommandHandlerBase {
    private final OwnerService ownerService;
    private final Scanner scanner;

    /**
     * Создаёт обработчик команды обновления владельца.
     *
     * @param ownerService сервис для работы с владельцами
     * @param scanner      источник пользовательского ввода
     */
    public UpdateOwnerCommandHandler(OwnerService ownerService, Scanner scanner) {
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    /**
     * Обрабатывает команду "update owner", создавая соответствующий объект команды.
     *
     * @param input строка, введённая пользователем
     * @return объект команды или передаёт выполнение следующему обработчику
     */
    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "update owner")) {
            return new UpdateOwnerCommand(ownerService, scanner);
        }
        return super.handle(input);
    }
}
