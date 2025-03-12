package Banking.Commands;

import Banking.Accounts.Account;
import Banking.Client;
import Banking.Printers.IPrinter;

import java.math.BigDecimal;
import java.util.UUID;

public class DepositCommand implements Command {
    private final Account account;
    private final BigDecimal amount;

    public DepositCommand(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute(IPrinter printer) {
        account.deposit(amount);
        printer.print("Deposit of " + amount + " to account " + account.getId() +
                ". New balance: " + account.getBalance());
    }
}
