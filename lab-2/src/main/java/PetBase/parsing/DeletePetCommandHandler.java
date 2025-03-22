package PetBase.parsing;

import PetBase.commands.Command;
import PetBase.commands.DeletePetCommand;
import PetBase.service.PetService;

import java.util.Objects;
import java.util.Scanner;

/**
 * Обработчик команды "delete pet".
 * Возвращает {@link DeletePetCommand}, если команда совпадает.
 */
public class DeletePetCommandHandler extends CommandHandlerBase {
    private final PetService petService;
    private final Scanner scanner;

    /**
     * Создаёт обработчик команды удаления питомца.
     *
     * @param petService сервис для работы с питомцами
     * @param scanner    источник пользовательского ввода
     */
    public DeletePetCommandHandler(PetService petService, Scanner scanner) {
        this.petService = petService;
        this.scanner = scanner;
    }

    /**
     * Обрабатывает команду "delete pet", создавая {@link DeletePetCommand}.
     * Если команда не совпадает, передаёт дальше по цепочке.
     *
     * @param input строка команды
     * @return подходящая команда или результат вызова следующего обработчика
     */
    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "delete pet")){
            return new DeletePetCommand(petService, scanner);
        }
        return super.handle(input);
    }
}
