package Banking.Parsing.BankOperations;

import Banking.Commands.BankOperations.TimeSkipCommand;
import Banking.Commands.Command;
import Banking.Parsing.CommandHandlerBase;

/**
 * Обработчик команды пропуска времени.
 * Обрабатывает команду в формате: time skip [days].
 */
public class TimeSkipCommandHandler extends CommandHandlerBase {

    /**
     * Обрабатывает входящую строку и возвращает команду пропуска времени,
     * если строка соответствует формату команды.
     *
     * @param input входящая строка для обработки
     * @return объект команды {@link TimeSkipCommand}, если строка соответствует формату, иначе передает запрос следующему обработчику
     */
    @Override
    public Command handle(String input) {
        // Формат: time skip [days]
        String[] parts = input.split(" ");
        if (parts.length == 3 && parts[0].equalsIgnoreCase("time") && parts[1].equalsIgnoreCase("skip")) {
            try {
                int daysToSkip = Integer.parseInt(parts[2]);
                return new TimeSkipCommand(daysToSkip);
            } catch (NumberFormatException e) {
                return super.handle(input);
            }
        }
        return super.handle(input);
    }
}