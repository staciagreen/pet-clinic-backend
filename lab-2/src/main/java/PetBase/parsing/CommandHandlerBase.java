package PetBase.parsing;

import PetBase.commands.Command;

/**
 * Базовая абстрактная реализация интерфейса {@link CommandHandler} для цепочки обязанностей.
 * Реализует логику передачи команды следующему обработчику, если текущий не справился.
 * Потомки должны переопределить метод {@code handle}, если хотят обрабатывать команду напрямую.
 */
public abstract class CommandHandlerBase implements CommandHandler {
    /**
     * Следующий обработчик в цепочке.
     */
    protected CommandHandler next;

    /**
     * Устанавливает следующий обработчик и возвращает его для цепочного вызова.
     *
     * @param next следующий обработчик
     * @return установленный обработчик
     */
    @Override
    public CommandHandler setNext(CommandHandler next) {
        this.next = next;
        return next;
    }

    /**
     * Обрабатывает команду или передаёт её следующему обработчику.
     *
     * @param input входная строка команды
     * @return объект {@link Command}, если команда обработана; иначе {@code null}
     */
    @Override
    public Command handle(String input) {
        return (next != null) ? next.handle(input) : null;
    }
}
