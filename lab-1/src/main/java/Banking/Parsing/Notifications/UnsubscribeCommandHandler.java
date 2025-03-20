package Banking.Parsing.Notifications;

import Banking.Bank;
import Banking.CentralBank;
import Banking.Client;
import Banking.Commands.Command;
import Banking.Commands.Notifications.UnsubscribeCommand;
import Banking.Parsing.CommandHandlerBase;

/**
 * Обработчик команды отписки от уведомлений.
 * Обрабатывает команду в формате: unsubscribe [clientName] [bankName].
 */
public class UnsubscribeCommandHandler extends CommandHandlerBase {

    /**
     * Конструктор по умолчанию.
     */
    public UnsubscribeCommandHandler() {
        // Default constructor
    }

    /**
     * Обрабатывает входящую строку и возвращает команду отписки от уведомлений,
     * если строка соответствует формату команды.
     *
     * @param input входящая строка для обработки
     * @return объект команды {@link UnsubscribeCommand}, если строка соответствует формату, иначе передает запрос следующему обработчику
     */
    @Override
    public Command handle(String input) {
        // Формат: unsubscribe [clientName] [bankName]
        String[] parts = input.split(" ");
        if (parts.length == 3 && parts[0].equalsIgnoreCase("unsubscribe")) {
            String clientName = parts[1];
            String bankName = parts[2];
            Bank bank = CentralBank.getInstance().findBankByName(bankName);
            if (bank != null) {
                Client client = bank.findClientByName(clientName);
                if (client != null) {
                    return new UnsubscribeCommand(client, bank);
                }
            }
        }
        return super.handle(input);
    }
}