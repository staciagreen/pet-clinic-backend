package Banking.History;

import Banking.Accounts.AccountSnapshot;
import Banking.Printers.IPrinter;

import java.util.Stack;

public class OperationHistory {
    private final Stack<AccountSnapshot> snapshots = new Stack<>();

    public void addSnapshot(AccountSnapshot snapshot) {
        snapshots.push(snapshot);
    }

    public AccountSnapshot undo() {
        if (!snapshots.isEmpty()) {
            return snapshots.pop();
        }
        return null;
    }

    public void showHistory(IPrinter printer) {
        printer.print("Operation History:");
        for (AccountSnapshot snapshot : snapshots) {
            printer.print("Account: " + snapshot.accountId() + ", Balance: " + snapshot.balance());
        }
    }
}

