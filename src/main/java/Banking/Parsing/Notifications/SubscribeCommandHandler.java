package Banking.Parsing.Notifications;

import Banking.Bank;
import Banking.CentralBank;
import Banking.Client;
import Banking.Commands.Command;
import Banking.Commands.Notifications.SubscribeCommand;
import Banking.Parsing.CommandHandlerBase;

/**
 * Обработчик команды подписки на уведомления.
 * Обрабатывает команду в формате: subscribe [clientName] [bankName].
 */
public class SubscribeCommandHandler extends CommandHandlerBase {
    /**
     * Обрабатывает входящую строку и возвращает команду подписки на уведомления,
     * если строка соответствует формату команды.
     *
     * @param input входящая строка для обработки
     * @return объект команды {@link SubscribeCommand}, если строка соответствует формату, иначе передает запрос следующему обработчику
     */
    @Override
    public Command handle(String input) {
        // Формат: subscribe [clientName] [bankName]
        String[] parts = input.split(" ");
        if (parts.length == 3 && parts[0].equalsIgnoreCase("subscribe")) {
            String clientName = parts[1];
            String bankName = parts[2];
            Bank bank = CentralBank.getInstance().findBankByName(bankName);
            if (bank != null) {
                Client client = bank.findClientByName(clientName);
                if (client != null) {
                    return new SubscribeCommand(client, bank);
                }
            }
        }
        return super.handle(input);
    }
}