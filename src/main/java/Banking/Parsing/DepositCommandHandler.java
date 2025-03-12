package Banking.Parsing;

import Banking.Accounts.Account;
import Banking.CentralBank;
import Banking.Commands.DepositCommand;
import Banking.Commands.Command;

import java.math.BigDecimal;
import java.util.UUID;

public class DepositCommandHandler extends CommandHandlerBase {
    @Override
    public Command handle(String input) {
        // Формат: deposit <accountID> <amount>
        String[] parts = input.split(" ");
        if (parts.length == 3 && parts[0].equalsIgnoreCase("deposit")) {
            String accountStr = parts[1];
            String amountStr = parts[2];

            UUID accountID;
            try {
                accountID = UUID.fromString(accountStr);
            } catch (IllegalArgumentException e) {
                return super.handle(input);
            }

            BigDecimal amount = new BigDecimal(amountStr);
            Account account = CentralBank.getInstance().findAccountByID(accountID);
            if (account != null) {
                return new DepositCommand(account, amount);
            }
        }
        return super.handle(input);
    }
}
