package PetBase.parsing;

import PetBase.commands.Command;
import PetBase.commands.ListPetsCommand;
import PetBase.service.PetService;

import java.util.Objects;

/**
 * Обработчик команды {@code "list pets"}.
 * Создаёт и возвращает {@link ListPetsCommand}, если введённая строка соответствует этой команде.
 */
public class ListPetsCommandHandler extends CommandHandlerBase {
    private final PetService petService;

    /**
     * Конструктор.
     *
     * @param petService сервис для работы с питомцами
     */
    public ListPetsCommandHandler(PetService petService) {
        this.petService = petService;
    }

    /**
     * Обрабатывает входную строку.
     * Если строка соответствует {@code "list pets"}, возвращает {@link ListPetsCommand},
     * иначе передаёт обработку следующему хендлеру в цепочке.
     *
     * @param input входная команда
     * @return соответствующая команда или результат следующего обработчика
     */
    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "list pets")) {
            return new ListPetsCommand(petService);
        }
        return super.handle(input);
    }
}
