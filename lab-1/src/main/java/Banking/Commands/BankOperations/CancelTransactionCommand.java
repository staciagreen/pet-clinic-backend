package Banking.Commands.BankOperations;

import Banking.Accounts.Account;
import Banking.Accounts.AccountSnapshot;
import Banking.CentralBank;
import Banking.Commands.Command;
import Banking.Printers.IPrinter;

/**
 * Команда для отмены последней операции.
 */
public class CancelTransactionCommand implements Command {
    /**
     * Выполняет команду отмены последней операции.
     *
     * @param printer принтер для вывода результатов выполнения команды
     */
    @Override
    public void execute(IPrinter printer) {
        AccountSnapshot snapshot = CentralBank.getInstance().getOperationHistory().undo();
        if (snapshot != null) {
            Account account = CentralBank.getInstance().findAccountByID(snapshot.accountId());
            if (account != null) {
                account.restoreSnapshot(snapshot);
                printer.print("Operation undone for account " + account.getId() +
                        ". Restored balance: " + account.getBalance());
            }
        } else {
            printer.print("No operation history available to undo.");
        }
    }
}