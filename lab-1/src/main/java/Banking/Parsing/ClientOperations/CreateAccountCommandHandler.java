package Banking.Parsing.ClientOperations;

import Banking.Bank;
import Banking.CentralBank;
import Banking.Client;
import Banking.Commands.ClientOperations.CreateAccountCommand;
import Banking.Commands.Command;
import Banking.Parsing.CommandHandlerBase;

/**
 * Обработчик команды для создания нового счета.
 */
public class CreateAccountCommandHandler extends CommandHandlerBase {
    /**
     * Обрабатывает входящую строку и возвращает команду создания счета.
     *
     * @param input входящая строка для обработки
     * @return объект команды CreateAccountCommand, если строка соответствует формату, иначе передает запрос следующему обработчику
     */
    @Override
    public Command handle(String input) {
        // Формат: create account [clientName] [accountType] [bankName]
        String[] parts = input.split(" ");
        if (parts.length >= 4 && parts[0].equals("create") && parts[1].equals("account")) {
            String clientName = parts[2];
            String accountType = parts[3];
            String bankName = parts[4];

            Bank bank = CentralBank.getInstance().findBankByName(bankName);
            if (bank != null) {
                Client client = bank.findClientByName(clientName);
                if (client != null) {
                    return new CreateAccountCommand(client, accountType, bank);
                }
            }
        }
        return super.handle(input);
    }
}