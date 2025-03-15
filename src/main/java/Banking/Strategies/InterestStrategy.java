package Banking.Strategies;

import java.math.BigDecimal;

/**
 * Интерфейс для стратегии расчета процентов.
 * Определяет метод для расчета процентов на основе текущего баланса.
 */
public interface InterestStrategy {
    /**
     * Рассчитывает проценты на основе текущего баланса.
     *
     * @param currentBalance текущий баланс счета
     * @return сумма начисленных процентов
     */
    BigDecimal calculateInterest(BigDecimal currentBalance);
}