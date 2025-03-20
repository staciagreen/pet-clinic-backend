package Banking.Parsing.BankOperations;

import Banking.Accounts.Account;
import Banking.CentralBank;
import Banking.Commands.BankOperations.DepositCommand;
import Banking.Commands.Command;
import Banking.Parsing.CommandHandlerBase;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Обработчик команды для пополнения счета.
 */
public class DepositCommandHandler extends CommandHandlerBase {
    /**
     * Обрабатывает входящую строку и возвращает команду пополнения счета.
     *
     * @param input входящая строка для обработки
     * @return объект команды DepositCommand, если строка соответствует формату, иначе передает запрос следующему обработчику
     */
    @Override
    public Command handle(String input) {
        // Формат: deposit [accountID] [amount]
        String[] parts = input.split(" ");
        if (parts.length == 3 && parts[0].equalsIgnoreCase("deposit")) {
            UUID accountID;
            try {
                accountID = UUID.fromString(parts[1]);
            } catch (IllegalArgumentException e) {
                return super.handle(input);
            }
            BigDecimal amount = new BigDecimal(parts[2]);
            Account account = CentralBank.getInstance().findAccountByID(accountID);
            if (account != null) {
                return new DepositCommand(account, amount);
            }
        }
        return super.handle(input);
    }
}