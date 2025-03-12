package Banking.Strategies;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DepositInterestStrategy implements InterestStrategy {
    @Override
    public BigDecimal calculateInterest(BigDecimal currentBalance) {
        BigDecimal rate;
        if (currentBalance.compareTo(BigDecimal.valueOf(50000)) < 0) {
            rate = BigDecimal.valueOf(0.03);
        } else if (currentBalance.compareTo(BigDecimal.valueOf(100000)) < 0) {
            rate = BigDecimal.valueOf(0.035);
        } else {
            rate = BigDecimal.valueOf(0.04);
        }
        BigDecimal dailyRate = rate.divide(BigDecimal.valueOf(365), 10, RoundingMode.HALF_UP);
        return currentBalance.multiply(dailyRate);
    }
}
