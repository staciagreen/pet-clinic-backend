package Banking.History;

import Banking.Accounts.AccountSnapshot;

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

    public void showHistory() {
        System.out.println("Operation History:");
        for (AccountSnapshot snapshot : snapshots) {
            System.out.println("Account: " + snapshot.getAccountId() +
                    ", Balance: " + snapshot.getBalance());
        }
    }
}

