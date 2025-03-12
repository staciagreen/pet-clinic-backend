package Banking;

import Banking.Accounts.Account;
import Banking.Observer.ObservableEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Bank extends ObservableEntity {
    private String name;
    private BigDecimal interestRate;
    private BigDecimal creditCommission;
    private BigDecimal transferLimit;
    private BigDecimal creditLimit;
    private List<Client> clients;
    private List<Account> accounts;
    // Можно добавить список транзакций для истории

    public Bank(String name, BigDecimal interestRate, BigDecimal creditCommission, BigDecimal creditLimit, BigDecimal transferLimit) {
        this.name = name;
        this.interestRate = interestRate;
        this.creditCommission = creditCommission;
        this.creditLimit = creditLimit;
        this.transferLimit = transferLimit;
        this.clients = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
        notifyObservers("Interest rate updated");
    }

    public void setCreditCommission(BigDecimal creditCommission) {
        this.creditCommission = creditCommission;
        notifyObservers("Credit commission updated");
    }

    public void setTransferLimit(BigDecimal transferLimit) {
        this.transferLimit = transferLimit;
        notifyObservers("Transfer limit updated");
    }

    public String getName() {
        return name;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public BigDecimal getCreditCommission() {
        return creditCommission;
    }

    public BigDecimal getTransferLimit() {
        return transferLimit;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public Client findClientByName(String clientName) {
        for (Client client : clients) {
            if (client.getName().equalsIgnoreCase(clientName)) {
                return client;
            }
        }
        return null;
    }
}
