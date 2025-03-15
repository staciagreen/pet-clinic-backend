package Banking.Parsing.BankOperations;

import Banking.Accounts.Account;
import Banking.CentralBank;
import Banking.Commands.BankOperations.TransferCommand;
import Banking.Commands.Command;
import Banking.Parsing.CommandHandlerBase;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Обработчик команды перевода средств между счетами.
 * Обрабатывает команду в формате: transfer [senderAccountID] [receiverAccountID] [amount].
 */
public class TransferCommandHandler extends CommandHandlerBase {

    /**
     * Конструктор по умолчанию.
     */
    public TransferCommandHandler() {
        // Default constructor
    }

    /**
     * Обрабатывает входящую строку и возвращает команду перевода средств,
     * если строка соответствует формату команды.
     *
     * @param input входящая строка для обработки
     * @return объект команды {@link TransferCommand}, если строка соответствует формату, иначе передает запрос следующему обработчику
     */
    @Override
    public Command handle(String input) {
        // Формат: transfer [senderAccountID] [receiverAccountID] [amount]
        String[] parts = input.split(" ");
        if (parts.length == 4 && parts[0].equalsIgnoreCase("transfer")) {
            Account sender;
            Account receiver;
            try {
                sender = CentralBank.getInstance().findAccountByID(UUID.fromString(parts[1]));
                receiver = CentralBank.getInstance().findAccountByID(UUID.fromString(parts[2]));
            } catch (IllegalArgumentException e) {
                return super.handle(input);
            }
            BigDecimal amount = new BigDecimal(parts[3]);
            if (sender != null && receiver != null) {
                return new TransferCommand(sender, receiver, amount);
            }
        }
        return super.handle(input);
    }
}