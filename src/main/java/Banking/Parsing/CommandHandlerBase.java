package Banking.Parsing;

import Banking.Commands.Command;

/**
 * Абстрактный базовый класс для обработчиков команд.
 * Реализует цепочку обязанностей (Chain of Responsibility) для обработки команд.
 */
public abstract class CommandHandlerBase implements CommandHandler {
    private CommandHandler nextHandler;

    /**
     * Устанавливает следующий обработчик в цепочке.
     *
     * @param handler следующий обработчик
     * @return следующий обработчик
     */
    @Override
    public CommandHandler setNext(CommandHandler handler) {
        this.nextHandler = handler;
        return handler;
    }

    /**
     * Обрабатывает входящую строку и передает ее следующему обработчику в цепочке,
     * если текущий обработчик не может обработать запрос.
     *
     * @param input входящая строка для обработки
     * @return объект команды, если обработчик смог обработать запрос, иначе null
     */
    @Override
    public Command handle(String input) {
        return (nextHandler != null) ? nextHandler.handle(input) : null;
    }
}