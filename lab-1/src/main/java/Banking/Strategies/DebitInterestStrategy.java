package Banking.Strategies;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Стратегия для расчета процентов по дебетовому счету.
 */
public class DebitInterestStrategy implements InterestStrategy {
    private final BigDecimal annualRate;

    /**
     * Конструктор стратегии.
     *
     * @param annualRate годовая процентная ставка
     */
    public DebitInterestStrategy(BigDecimal annualRate) {
        this.annualRate = annualRate;
    }

    /**
     * Рассчитывает ежедневные проценты на основе текущего баланса.
     *
     * @param currentBalance текущий баланс
     * @return сумма начисленных процентов
     */
    @Override
    public BigDecimal calculateInterest(BigDecimal currentBalance) {
        BigDecimal dailyRate = annualRate.divide(BigDecimal.valueOf(365), 10, RoundingMode.HALF_UP);
        return currentBalance.multiply(dailyRate);
    }
}