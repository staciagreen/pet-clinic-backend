package Banking.Strategies;

import java.math.BigDecimal;

/**
 * Стратегия для расчета комиссии по кредитному счету.
 */
public class CreditCommissionStrategy {
    private final BigDecimal commissionFee;

    /**
     * Конструктор стратегии.
     *
     * @param commissionFee размер комиссии
     */
    public CreditCommissionStrategy(BigDecimal commissionFee) {
        this.commissionFee = commissionFee;
    }

    /**
     * Применяет комиссию к балансу, если баланс отрицательный.
     *
     * @param balance текущий баланс
     * @return размер комиссии, если баланс отрицательный, иначе 0
     */
    public BigDecimal applyCommission(BigDecimal balance) {
        return balance.compareTo(BigDecimal.ZERO) < 0 ? commissionFee : BigDecimal.ZERO;
    }
}