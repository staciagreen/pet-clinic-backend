package Banking;

import Banking.Accounts.Account;
import Banking.Observer.ObservableEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий банк.
 */
public class Bank extends ObservableEntity {
    private String name;
    private BigDecimal interestRate;
    private BigDecimal creditCommission;
    private BigDecimal transferLimit;
    private BigDecimal creditLimit;
    private List<Client> clients;
    private List<Account> accounts;

    /**
     * Конструктор банка.
     *
     * @param name название банка
     * @param interestRate процентная ставка
     * @param creditCommission комиссия за кредит
     * @param creditLimit кредитный лимит
     * @param transferLimit лимит на переводы
     */
    public Bank(String name, BigDecimal interestRate, BigDecimal creditCommission, BigDecimal creditLimit, BigDecimal transferLimit) {
        this.name = name;
        this.interestRate = interestRate;
        this.creditCommission = creditCommission;
        this.creditLimit = creditLimit;
        this.transferLimit = transferLimit;
        this.clients = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    /**
     * Возвращает список счетов банка.
     *
     * @return список счетов
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Добавляет клиента в банк.
     *
     * @param client клиент для добавления
     */
    public void addClient(Client client) {
        clients.add(client);
    }

    /**
     * Добавляет счет в банк.
     *
     * @param account счет для добавления
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * Устанавливает новую процентную ставку.
     *
     * @param interestRate новая процентная ставка
     */
    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
        notifyObservers("Interest rate updated");
    }

    /**
     * Устанавливает новую комиссию за кредит.
     *
     * @param creditCommission новая комиссия за кредит
     */
    public void setCreditCommission(BigDecimal creditCommission) {
        this.creditCommission = creditCommission;
        notifyObservers("Credit commission updated");
    }

    /**
     * Устанавливает новый лимит на переводы.
     *
     * @param transferLimit новый лимит на переводы
     */
    public void setTransferLimit(BigDecimal transferLimit) {
        this.transferLimit = transferLimit;
        notifyObservers("Transfer limit updated");
    }

    /**
     * Устанавливает новый кредитный лимит.
     *
     * @param newCreditLimit новый кредитный лимит
     */
    public void setCreditLimit(BigDecimal newCreditLimit) {
        this.creditLimit = newCreditLimit;
        notifyObservers("Credit limit updated");
    }

    /**
     * Возвращает название банка.
     *
     * @return название банка
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает текущую процентную ставку.
     *
     * @return процентная ставка
     */
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    /**
     * Возвращает текущую комиссию за кредит.
     *
     * @return комиссия за кредит
     */
    public BigDecimal getCreditCommission() {
        return creditCommission;
    }

    /**
     * Возвращает текущий лимит на переводы.
     *
     * @return лимит на переводы
     */
    public BigDecimal getTransferLimit() {
        return transferLimit;
    }

    /**
     * Возвращает текущий кредитный лимит.
     *
     * @return кредитный лимит
     */
    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    /**
     * Находит клиента по имени.
     *
     * @param clientName имя клиента
     * @return найденный клиент или null, если клиент не найден
     */
    public Client findClientByName(String clientName) {
        for (Client client : clients) {
            if (client.getName().equalsIgnoreCase(clientName)) {
                return client;
            }
        }
        return null;
    }
}