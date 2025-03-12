package Banking.Strategies;

import java.math.BigDecimal;

public class CreditCommissionStrategy {
    private final BigDecimal commissionFee;

    public CreditCommissionStrategy(BigDecimal commissionFee) {
        this.commissionFee = commissionFee;
    }

    public BigDecimal applyCommission(BigDecimal balance) {
        return balance.compareTo(BigDecimal.ZERO) < 0 ? commissionFee : BigDecimal.ZERO;
    }
}
