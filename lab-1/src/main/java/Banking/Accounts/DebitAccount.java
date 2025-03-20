package Banking.Accounts;

import Banking.Bank;
import Banking.Client;
import Banking.Commands.Command;
import Banking.Strategies.DebitInterestStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс, представляющий дебетовый счет.
 * Дебетовый счет позволяет клиенту хранить средства и начисляет проценты на остаток.
 */
public class DebitAccount implements Account {
    private final UUID id;
    private final Client owner;
    private AccountState state;
    private BigDecimal balance;
    private final Bank bank;
    private final DebitInterestStrategy interestStrategy;

    /**
     * Конструктор для создания дебетового счета.
     *
     * @param owner владелец счета
     * @param bank банк, в котором открыт счет
     */
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

    /**
     * Пополняет счет на указанную сумму.
     *
     * @param amount сумма для пополнения
     */
    @Override
    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    /**
     * Снимает указанную сумму со счета.
     *
     * @param amount сумма для снятия
     * @throws IllegalArgumentException если на счете недостаточно средств
     */
    @Override
    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
        } else {
            throw new IllegalArgumentException("Недостаточно средств для снятия.");
        }
    }

    /**
     * Начисляет ежедневные проценты на остаток счета.
     */
    public void accrueDailyInterest() {
        BigDecimal interest = interestStrategy.calculateInterest(balance);
        balance = balance.add(interest);
    }

    /**
     * Создает снимок текущего состояния счета.
     *
     * @return снимок состояния счета
     */
    @Override
    public AccountSnapshot createSnapshot() {
        return new AccountSnapshot(this.id, this.balance, this.state);
    }

    /**
     * Восстанавливает состояние счета из снимка.
     *
     * @param snapshot снимок состояния счета
     */
    @Override
    public void restoreSnapshot(AccountSnapshot snapshot) {
        this.balance = snapshot.balance();
        this.state = snapshot.state();
    }

    /**
     * Возвращает уникальный идентификатор счета.
     *
     * @return уникальный идентификатор счета
     */
    @Override
    public UUID getId() {
        return id;
    }

    /**
     * Возвращает владельца счета.
     *
     * @return объект владельца счета
     */
    @Override
    public Client getOwner() {
        return owner;
    }

    /**
     * Возвращает текущий баланс счета.
     *
     * @return текущий баланс счета
     */
    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Возвращает текущее состояние счета.
     *
     * @return состояние счета
     */
    @Override
    public AccountState getState() {
        return state;
    }

    /**
     * Возвращает тип счета.
     *
     * @return название типа счета
     */
    @Override
    public String getAccountTypeName() {
        return "debit";
    }

    /**
     * Возвращает банк, в котором открыт счет.
     *
     * @return объект банка
     */
    @Override
    public Bank getBank() {
        return bank;
    }

    /**
     * Обновляет состояние счета на основе информации о клиенте.
     * Если клиент предоставил полную информацию, счет переходит в активное состояние.
     *
     * @param message сообщение для обновления (не используется)
     */
    @Override
    public void update(String message) {
        if (owner.hasFullInfo()) {
            state = new AccountState.ActiveState();
        }
    }
}