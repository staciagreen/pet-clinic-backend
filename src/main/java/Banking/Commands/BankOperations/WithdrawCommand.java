package Banking.Commands.BankOperations;

import Banking.Accounts.Account;
import Banking.Commands.Command;
import Banking.Printers.IPrinter;
import Banking.CentralBank;

import java.math.BigDecimal;

public class WithdrawCommand implements Command {
    private final Account account;
    private final BigDecimal amount;

    public WithdrawCommand(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute(IPrinter printer) {
        try {
            CentralBank.getInstance().getOperationHistory().addSnapshot(account.createSnapshot());
            account.withdraw(amount);
            printer.print("Withdrawn " + amount + " from account " + account.getId() +
                    ". New balance: " + account.getBalance());
        } catch (Exception e) {
            printer.print("Withdrawal failed: " + e.getMessage());
        }
    }
}
