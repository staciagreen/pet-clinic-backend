package Banking.Accounts;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Класс, представляющий снимок состояния счета.
 * Используется для сохранения и восстановления состояния счета.
 *
 * @param accountId уникальный идентификатор счета
 * @param balance текущий баланс счета
 * @param state состояние счета
 */
public record AccountSnapshot(UUID accountId, BigDecimal balance, AccountState state) {
}