package Banking;

import Banking.Accounts.Account;

import java.math.BigDecimal;

/**
 * Интерфейс для стратегии расчета процентов.
 */
public interface InterestStrategy {
    /**
     * Рассчитывает проценты на основе текущего баланса.
     *
     * @param account счет, для которого рассчитываются проценты
     * @param balance текущий баланс
     * @return сумма начисленных процентов
     */
    BigDecimal calculateInterest(Account account, BigDecimal balance);
}