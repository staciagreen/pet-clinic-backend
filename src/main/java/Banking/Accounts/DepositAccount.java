package Banking.Accounts;

import Banking.Bank;
import Banking.Client;
import Banking.Commands.Command;
import Banking.Strategies.DepositInterestStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DepositAccount implements Account {
    private final UUID id;
    private final Client owner;
    private AccountState state;
    private BigDecimal balance;
    private final List<Command> transactionHistory;
    private final Bank bank;
    private final String accountTypeName = "deposit";
    private final DepositInterestStrategy interestStrategy;

    public DepositAccount(Client owner, Bank bank) {
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.bank = bank;
        this.balance = BigDecimal.ZERO;
        this.transactionHistory = new ArrayList<>();
        this.state = owner.hasFullInfo() ? new AccountState.ActiveState() : new AccountState.SuspiciousState();
        owner.addAccount(this);
        this.interestStrategy = new DepositInterestStrategy();
    }

    @Override
    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        throw new UnsupportedOperationException("Снятие с депозитного счета запрещено до окончания срока.");
    }

    public void accrueDailyInterest() {
        BigDecimal interest = interestStrategy.calculateInterest(balance);
        balance = balance.add(interest);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Client getOwner() {
        return owner;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public AccountState getState() {
        return state;
    }

    @Override
    public String getAccountTypeName() {
        return accountTypeName;
    }

    @Override
    public Bank getBank() {
        return bank;
    }

    @Override
    public void update(String message) {
        // При необходимости обновить состояние
    }
}
