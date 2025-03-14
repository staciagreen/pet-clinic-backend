package Banking.Accounts;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountSnapshot(UUID accountId, BigDecimal balance, AccountState state) {
}
