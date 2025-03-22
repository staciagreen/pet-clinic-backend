package PetBase.parsing;

import PetBase.commands.Command;

/**
 * Интерфейс для обработки команд в цепочке обязанностей.
 * Каждый обработчик пытается распознать команду и вернуть соответствующий объект {@link Command}.
 */
public interface CommandHandler {

    /**
     * Устанавливает следующий обработчик в цепочке.
     *
     * @param next следующий обработчик
     * @return установленный следующий обработчик (для удобной цепочки вызовов)
     */
    CommandHandler setNext(CommandHandler next);

    /**
     * Обрабатывает входную строку и возвращает соответствующую команду.
     * Если обработчик не может распознать команду, он передаёт её дальше по цепочке.
     *
     * @param input входная строка команды
     * @return объект {@link Command}, если команда распознана; иначе {@code null}
     */
    Command handle(String input);
}
