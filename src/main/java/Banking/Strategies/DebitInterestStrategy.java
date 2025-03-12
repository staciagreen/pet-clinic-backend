package Banking.Strategies;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DebitInterestStrategy implements InterestStrategy {
    private final BigDecimal annualRate;

    public DebitInterestStrategy(BigDecimal annualRate) {
        this.annualRate = annualRate;
    }

    @Override
    public BigDecimal calculateInterest(BigDecimal currentBalance) {
        BigDecimal dailyRate = annualRate.divide(BigDecimal.valueOf(365), 10, RoundingMode.HALF_UP);
        return currentBalance.multiply(dailyRate);
    }
}
