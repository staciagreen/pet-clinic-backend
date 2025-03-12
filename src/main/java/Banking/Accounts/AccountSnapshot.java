package Banking.Accounts;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountSnapshot {
    private final UUID accountId;
    private final BigDecimal balance;
    private final AccountState state;

    public AccountSnapshot(UUID accountId, BigDecimal balance, AccountState state) {
        this.accountId = accountId;
        this.balance = balance;
        this.state = state;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountState getState() {
        return state;
    }
}
