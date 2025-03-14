package Banking;

import Banking.Accounts.Account;
import Banking.History.OperationHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CentralBank {
    private static CentralBank instance;
    private List<Bank> banks;
    private OperationHistory operationHistory = new OperationHistory();

    private CentralBank() {
        banks = new ArrayList<>();
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

    public Bank findBankByName(String bankName) {
        for (Bank bank : banks) {
            if (bank.getName().equalsIgnoreCase(bankName)) {
                return bank;
            }
        }
        return null;
    }

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

    public void clearAllBanks() {
        banks.clear();
    }
}
