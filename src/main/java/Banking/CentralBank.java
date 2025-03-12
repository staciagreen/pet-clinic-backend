package Banking;

import Banking.Accounts.Account;
import Banking.History.OperationHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CentralBank {
    private static CentralBank instance;
    private List<Bank> banks;
    private List<Account> accounts;
    private OperationHistory operationHistory = new OperationHistory();

    private CentralBank() {
        banks = new ArrayList<>();
        accounts = new ArrayList<>();
    }

    public static CentralBank getInstance() {
        if (instance == null) {
            instance = new CentralBank();
        }
        return instance;
    }

    public OperationHistory getOperationHistory() {
        return operationHistory;
    }

    public void registerBank(Bank bank) {
        banks.add(bank);
    }


    private Bank findBankById(String bankId) {
        for (Bank bank : banks) {
            if (bank.getName().equalsIgnoreCase(bankId)) {
                return bank;
            }
        }
        return null;
    }

    public Bank findBankByName(String bankName) {
        for (Bank bank : banks) {
            if (bank.getName().equalsIgnoreCase(bankName)) {
                return bank;
            }
        }
        return null;
    }

    public Account findAccountByID(UUID accountId) {
        for (Account account : accounts) {
            if (account.getId().equals(accountId)) {
                return account;
            }
        }
        return null;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void notifyBanksForPeriodUpdate(int days) {
        for (Bank bank : banks) {
            for (var account : bank.getAccounts()) {
                // Если счет дебетовый или депозитный, вызываем метод начисления процентов days раз
                if (account instanceof Banking.Accounts.DebitAccount) {
                    for (int i = 0; i < days; i++) {
                        ((Banking.Accounts.DebitAccount) account).accrueDailyInterest();
                    }
                } else if (account instanceof Banking.Accounts.DepositAccount) {
                    for (int i = 0; i < days; i++) {
                        ((Banking.Accounts.DepositAccount) account).accrueDailyInterest();
                    }
                }
                // Для кредитного счета (если требуется) можно добавить логику начисления комиссии
            }
        }
    }
}
