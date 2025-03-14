package Banking.Accounts;

import Banking.Bank;
import Banking.Client;
import Banking.Commands.Command;
import Banking.Strategies.DebitInterestStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DebitAccount implements Account {
    private final UUID id;
    private final Client owner;
    private AccountState state;
    private BigDecimal balance;
    private final Bank bank;
    private final DebitInterestStrategy interestStrategy;

    public DebitAccount(Client owner, Bank bank) {
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.bank = bank;
        this.balance = BigDecimal.ZERO;
        List<Command> transactionHistory = new ArrayList<>();
        this.state = owner.hasFullInfo() ? new AccountState.ActiveState() : new AccountState.SuspiciousState();
        owner.addAccount(this);
        this.interestStrategy = new DebitInterestStrategy(bank.getInterestRate());
    }

    @Override
    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
        } else {
            throw new IllegalArgumentException("Недостаточно средств для снятия.");
        }
    }

    public void accrueDailyInterest() {
        BigDecimal interest = interestStrategy.calculateInterest(balance);
        balance = balance.add(interest);
    }
    // Метод создания снимка состояния счета
    public AccountSnapshot createSnapshot() {
        return new AccountSnapshot(this.id, this.balance, this.state);
    }

    // Метод восстановления состояния счета из снимка
    public void restoreSnapshot(AccountSnapshot snapshot) {
        this.balance = snapshot.balance();
        this.state = snapshot.state();
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
        return "debit";
    }

    @Override
    public Bank getBank() {
        return bank;
    }

    @Override
    public void update(String message) {
        if (owner.hasFullInfo()) {
            state = new AccountState.ActiveState();
        }
    }
}
