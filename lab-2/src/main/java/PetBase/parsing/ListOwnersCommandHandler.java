package PetBase.parsing;

import PetBase.commands.Command;
import PetBase.commands.ListOwnersCommand;
import PetBase.service.OwnerService;

import java.util.Objects;

/**
 * Обработчик команды {@code "list owners"}.
 * Возвращает {@link ListOwnersCommand}, если команда соответствует ожидаемой.
 */
public class ListOwnersCommandHandler extends CommandHandlerBase {
    private final OwnerService ownerService;

    /**
     * Конструктор.
     *
     * @param ownerService сервис для работы с владельцами
     */
    public ListOwnersCommandHandler(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    /**
     * Обрабатывает команду пользователя.
     * Если введена {@code "list owners"}, возвращает соответствующую команду,
     * иначе передаёт обработку следующему хендлеру в цепочке.
     *
     * @param input строка команды
     * @return объект {@link Command} или результат следующего обработчика
     */
    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "list owners")) {
            return new ListOwnersCommand(ownerService);
        }
        return super.handle(input);
    }
}
