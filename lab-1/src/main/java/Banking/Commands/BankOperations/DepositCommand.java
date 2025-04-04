package Banking.Commands.BankOperations;

import Banking.Accounts.Account;
import Banking.Commands.Command;
import Banking.Printers.IPrinter;

import java.math.BigDecimal;

/**
 * Команда для пополнения счета.
 */
public class DepositCommand implements Command {
    private final Account account;
    private final BigDecimal amount;

    /**
     * Конструктор команды.
     *
     * @param account счет, на который вносятся средства
     * @param amount сумма для пополнения
     */
    public DepositCommand(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }

    /**
     * Выполняет команду пополнения счета.
     *
     * @param printer принтер для вывода результатов выполнения команды
     */
    @Override
    public void execute(IPrinter printer) {
        account.deposit(amount);
        printer.print("Deposit of " + amount + " to account " + account.getId() +
                ". New balance: " + account.getBalance());
    }
}