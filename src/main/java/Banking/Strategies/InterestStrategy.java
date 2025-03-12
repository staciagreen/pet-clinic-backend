package Banking.Strategies;

import java.math.BigDecimal;

public interface InterestStrategy {
    BigDecimal calculateInterest(BigDecimal currentBalance);
}
