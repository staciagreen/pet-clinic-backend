package Banking.Accounts;

import Banking.Bank;
import Banking.Client;
import Banking.Commands.Command;
import Banking.Strategies.CreditCommissionStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreditAccount implements Account {
    private final UUID id;
    private final Client owner;
    private AccountState state;
    private final List<Command> transactionHistory;
    private final Bank bank;
    private BigDecimal balance;
    private final String accountTypeName = "credit";
    private final CreditCommissionStrategy commissionStrategy;
    private final BigDecimal creditLimit;

    public CreditAccount(Client owner, Bank bank) {
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.bank = bank;
        this.balance = BigDecimal.ZERO;
        this.transactionHistory = new ArrayList<>();
        this.state = owner.hasFullInfo() ? new AccountState.ActiveState() : new AccountState.SuspiciousState();
        owner.addAccount(this);
        this.creditLimit = bank.getCreditLimit();
        this.commissionStrategy = new CreditCommissionStrategy(bank.getCreditCommission());
    }

    @Override
    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        BigDecimal newBalance = balance.subtract(amount);
        if (newBalance.compareTo(creditLimit.negate()) >= 0) {
            balance = newBalance;
            if (balance.compareTo(BigDecimal.ZERO) < 0) {
                BigDecimal commission = commissionStrategy.applyCommission(balance);
                balance = balance.subtract(commission);
            }
        } else {
            throw new IllegalArgumentException("Превышен кредитный лимит.");
        }
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
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public void update(String message) {
        state = owner.hasFullInfo() ? new AccountState.ActiveState() : new AccountState.SuspiciousState();
    }
}
