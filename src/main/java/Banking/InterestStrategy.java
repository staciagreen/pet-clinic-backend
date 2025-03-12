package Banking;

import Banking.Accounts.Account;

import java.math.BigDecimal;

public interface InterestStrategy {
    BigDecimal calculateInterest(Account account, BigDecimal balance);
}
