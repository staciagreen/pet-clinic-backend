package Banking.Commands.BankOperations;

import Banking.Accounts.Account;
import Banking.Commands.Command;
import Banking.Printers.IPrinter;

import java.math.BigDecimal;

/**
 * Команда для перевода средств между счетами.
 */
public class TransferCommand implements Command {
    private final Account sender;
    private final Account receiver;
    private final BigDecimal amount;

    /**
     * Конструктор команды.
     *
     * @param sender счет отправителя
     * @param receiver счет получателя
     * @param amount сумма для перевода
     */
    public TransferCommand(Account sender, Account receiver, BigDecimal amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    /**
     * Выполняет команду перевода средств.
     *
     * @param printer принтер для вывода результатов выполнения команды
     */
    @Override
    public void execute(IPrinter printer) {
        try {
            sender.withdraw(amount);
            receiver.deposit(amount);
            printer.print("Transferred " + amount + " from account " + sender.getId() +
                    " to account " + receiver.getId());
        } catch (Exception e) {
            printer.print("Transfer failed: " + e.getMessage());
        }
    }
}