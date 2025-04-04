package Banking;

import Banking.Accounts.Account;
import Banking.History.OperationHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс, представляющий центральный банк.
 */
public class CentralBank {
    private static CentralBank instance;
    private List<Bank> banks;
    private OperationHistory operationHistory = new OperationHistory();

    private CentralBank() {
        banks = new ArrayList<>();
    }

    /**
     * Возвращает единственный экземпляр CentralBank (реализация Singleton).
     *
     * @return экземпляр CentralBank
     */
    public static CentralBank getInstance() {
        if (instance == null) {
            instance = new CentralBank();
        }
        return instance;
    }

    /**
     * Возвращает историю операций.
     *
     * @return история операций
     */
    public OperationHistory getOperationHistory() {
        return operationHistory;
    }

    /**
     * Регистрирует новый банк в центральном банке.
     *
     * @param bank банк для регистрации
     */
    public void registerBank(Bank bank) {
        banks.add(bank);
    }

    /**
     * Находит банк по названию.
     *
     * @param bankName название банка
     * @return найденный банк или null, если банк не найден
     */
    public Bank findBankByName(String bankName) {
        for (Bank bank : banks) {
            if (bank.getName().equalsIgnoreCase(bankName)) {
                return bank;
            }
        }
        return null;
    }

    /**
     * Находит счет по идентификатору.
     *
     * @param accountId идентификатор счета
     * @return найденный счет или null, если счет не найден
     */
    public Account findAccountByID(UUID accountId) {
        for (Bank bank : banks) {
            for (Account account : bank.getAccounts()) {
                if (account.getId().equals(accountId)) {
                    return account;
                }
            }
        }
        return null;
    }

    /**
     * Уведомляет банки о пропуске времени.
     *
     * @param days количество дней для пропуска
     */
    public void notifyBanksForPeriodUpdate(int days) {
        for (Bank bank : banks) {
            for (var account : bank.getAccounts()) {
                if (account instanceof Banking.Accounts.DebitAccount) {
                    for (int i = 0; i < days; i++) {
                        ((Banking.Accounts.DebitAccount) account).accrueDailyInterest();
                    }
                } else if (account instanceof Banking.Accounts.DepositAccount) {
                    for (int i = 0; i < days; i++) {
                        ((Banking.Accounts.DepositAccount) account).accrueDailyInterest();
                    }
                }
            }
        }
    }

    /**
     * Очищает список всех банков.
     */
    public void clearAllBanks() {
        banks.clear();
    }
}