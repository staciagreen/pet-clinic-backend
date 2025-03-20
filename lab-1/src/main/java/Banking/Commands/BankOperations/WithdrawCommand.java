package Banking.Commands.BankOperations;

import Banking.Accounts.Account;
import Banking.Commands.Command;
import Banking.Printers.IPrinter;
import Banking.CentralBank;

import java.math.BigDecimal;

/**
 * Команда для снятия средств со счета.
 */
public class WithdrawCommand implements Command {
    private final Account account;
    private final BigDecimal amount;

    /**
     * Конструктор команды.
     *
     * @param account счет, с которого снимаются средства
     * @param amount сумма для снятия
     */
    public WithdrawCommand(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }

    /**
     * Выполняет команду снятия средств со счета.
     *
     * @param printer принтер для вывода результатов выполнения команды
     */
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