package Banking.Accounts;

import Banking.Bank;
import Banking.Client;
import Banking.Observer.Observer;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Интерфейс, представляющий банковский счет.
 * Счет может быть использован для хранения средств, выполнения операций пополнения и снятия,
 * а также для создания и восстановления снимков состояния счета.
 */
public interface Account extends Observer {
    /**
     * Возвращает уникальный идентификатор счета.
     *
     * @return уникальный идентификатор счета
     */
    UUID getId();

    /**
     * Возвращает владельца счета.
     *
     * @return объект владельца счета
     */
    Client getOwner();

    /**
     * Возвращает текущий баланс счета.
     *
     * @return текущий баланс счета
     */
    BigDecimal getBalance();

    /**
     * Возвращает состояние счета.
     *
     * @return состояние счета
     */
    AccountState getState();

    /**
     * Возвращает тип счета в виде строки.
     *
     * @return название типа счета
     */
    String getAccountTypeName();

    /**
     * Возвращает банк, в котором открыт счет.
     *
     * @return объект банка
     */
    Bank getBank();

    /**
     * Пополняет счет на указанную сумму.
     *
     * @param amount сумма для пополнения
     */
    void deposit(BigDecimal amount);

    /**
     * Снимает указанную сумму со счета.
     *
     * @param amount сумма для снятия
     */
    void withdraw(BigDecimal amount);

    /**
     * Создает снимок текущего состояния счета.
     *
     * @return снимок состояния счета
     */
    AccountSnapshot createSnapshot();

    /**
     * Восстанавливает состояние счета из снимка.
     *
     * @param snapshot снимок состояния счета
     */
    void restoreSnapshot(AccountSnapshot snapshot);
}