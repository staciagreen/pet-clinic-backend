package Banking.Commands;

import Banking.Accounts.Account;
import Banking.Accounts.AccountSnapshot;
import Banking.CentralBank;
import Banking.Printers.IPrinter;

public class CancelTransactionCommand implements Command {
    @Override
    public void execute(IPrinter printer) {
        AccountSnapshot snapshot = CentralBank.getInstance().getOperationHistory().undo();
        if (snapshot != null) {
            Account account = CentralBank.getInstance().findAccountByID(snapshot.getAccountId());
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
