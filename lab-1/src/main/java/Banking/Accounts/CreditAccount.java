package Banking.Accounts;

import Banking.Bank;
import Banking.Client;
import Banking.Commands.Command;
import Banking.Strategies.CreditCommissionStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс, представляющий кредитный счет.
 * Кредитный счет позволяет клиенту использовать средства сверх текущего баланса,
 * но с ограничением по кредитному лимиту и начислением комиссии за использование кредитных средств.
 */
public class CreditAccount implements Account {
    private final UUID id;
    private final Client owner;
    private AccountState state;
    private final Bank bank;
    private BigDecimal balance;
    private final CreditCommissionStrategy commissionStrategy;
    private final BigDecimal creditLimit;

    /**
     * Конструктор для создания кредитного счета.
     *
     * @param owner владелец счета
     * @param bank банк, в котором открыт счет
     */
    public CreditAccount(Client owner, Bank bank) {
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.bank = bank;
        this.balance = BigDecimal.ZERO;
        List<Command> transactionHistory = new ArrayList<>();
        this.state = owner.hasFullInfo() ? new AccountState.ActiveState() : new AccountState.SuspiciousState();
        owner.addAccount(this);
        this.creditLimit = bank.getCreditLimit();
        this.commissionStrategy = new CreditCommissionStrategy(bank.getCreditCommission());
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
     * Если баланс становится отрицательным, начисляется комиссия.
     *
     * @param amount сумма для снятия
     * @throws IllegalArgumentException если сумма снятия превышает кредитный лимит
     */
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
        return "credit";
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
     * Возвращает текущий баланс счета.
     *
     * @return текущий баланс счета
     */
    @Override
    public BigDecimal getBalance() {
        return balance;
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