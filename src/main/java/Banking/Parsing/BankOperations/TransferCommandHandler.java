package Banking.Parsing.BankOperations;

import Banking.Accounts.Account;
import Banking.CentralBank;
import Banking.Commands.BankOperations.TransferCommand;
import Banking.Commands.Command;
import Banking.Parsing.CommandHandlerBase;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferCommandHandler extends CommandHandlerBase {
    @Override
    public Command handle(String input) {
        // Формат: transfer <senderAccountID> <receiverAccountID> <amount>
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
