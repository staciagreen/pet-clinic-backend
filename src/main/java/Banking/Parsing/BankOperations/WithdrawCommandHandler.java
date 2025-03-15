package Banking.Parsing.BankOperations;

import Banking.Accounts.Account;
import Banking.CentralBank;
import Banking.Commands.BankOperations.WithdrawCommand;
import Banking.Commands.Command;
import Banking.Parsing.CommandHandlerBase;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Обработчик команды для снятия средств со счета.
 * Обрабатывает команду в формате: withdraw [accountID] [amount].
 */
public class WithdrawCommandHandler extends CommandHandlerBase {
    /**
     * Обрабатывает входящую строку и возвращает команду снятия средств,
     * если строка соответствует формату команды.
     *
     * @param input входящая строка для обработки
     * @return объект команды {@link WithdrawCommand}, если строка соответствует формату, иначе передает запрос следующему обработчику
     */
    @Override
    public Command handle(String input) {
        // Формат: withdraw [accountID] [amount]
        String[] parts = input.split(" ");
        if (parts.length == 3 && parts[0].equalsIgnoreCase("withdraw")) {
            UUID accountID;
            try {
                accountID = UUID.fromString(parts[1]);
            } catch (IllegalArgumentException e) {
                return super.handle(input);
            }
            BigDecimal amount = new BigDecimal(parts[2]);
            Account account = CentralBank.getInstance().findAccountByID(accountID);
            if (account != null) {
                return new WithdrawCommand(account, amount);
            }
        }
        return super.handle(input);
    }
}