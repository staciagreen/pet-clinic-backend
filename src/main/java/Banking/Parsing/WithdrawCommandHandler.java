package Banking.Parsing;

import Banking.Accounts.Account;
import Banking.CentralBank;
import Banking.Commands.WithdrawCommand;
import Banking.Commands.Command;

import java.math.BigDecimal;
import java.util.UUID;

public class WithdrawCommandHandler extends CommandHandlerBase {
    @Override
    public Command handle(String input) {
        // Формат: withdraw <accountID> <amount>
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