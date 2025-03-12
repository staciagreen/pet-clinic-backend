package Banking.Accounts;

import Banking.Bank;
import Banking.Client;
import Banking.Observer.Observer;

import java.math.BigDecimal;
import java.util.UUID;

public interface Account extends Observer {
    UUID getId();

    Client getOwner();

    BigDecimal getBalance();

    AccountState getState();

    String getAccountTypeName();

    Bank getBank();

    void deposit(BigDecimal amount);

    void withdraw(BigDecimal amount);

    public AccountSnapshot createSnapshot();

    public void restoreSnapshot(AccountSnapshot snapshot);
}
