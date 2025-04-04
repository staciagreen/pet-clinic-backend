package Banking;

import Banking.Accounts.Account;
import Banking.Observer.ObservableEntity;
import Banking.Observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс, представляющий клиента банка.
 */
public class Client extends ObservableEntity implements Observer {
    private String firstName;
    private String lastName;
    private String address;
    private String passport;
    private final UUID id;
    private final List<Account> accounts = new ArrayList<>();

    private Client(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("Имя и фамилия обязательны.");
        }
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Добавляет счет клиенту.
     *
     * @param account счет для добавления
     */
    public void addAccount(Account account) {
        if (!accounts.contains(account)) {
            accounts.add(account);
        }
    }

    /**
     * Удаляет счет у клиента.
     *
     * @param account счет для удаления
     */
    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    /**
     * Возвращает полное имя клиента.
     *
     * @return полное имя клиента
     */
    public String GetFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Возвращает адрес клиента.
     *
     * @return адрес клиента
     */
    public String getAddress() {
        return address;
    }

    /**
     * Возвращает паспортные данные клиента.
     *
     * @return паспортные данные клиента
     */
    public String getPassport() {
        return passport;
    }

    /**
     * Возвращает имя клиента.
     *
     * @return имя клиента
     */
    public String getName() {
        return firstName + lastName;
    }

    /**
     * Устанавливает адрес клиента.
     *
     * @param address новый адрес
     */
    public void SetAddress(String address) {
        this.address = address;
        notifyObservers("Address updated");
    }

    /**
     * Устанавливает паспортные данные клиента.
     *
     * @param passport новые паспортные данные
     */
    public void SetPassport(String passport) {
        this.passport = passport;
        notifyObservers("Passport updated");
    }

    /**
     * Проверяет, заполнена ли вся информация о клиенте.
     *
     * @return true, если адрес и паспортные данные заполнены, иначе false
     */
    public boolean hasFullInfo() {
        return address != null && passport != null;
    }

    /**
     * Реакция на уведомления.
     *
     * @param message сообщение об изменении
     */
    @Override
    public void update(String message) {
        // Реакция на уведомления
    }

    /**
     * Внутренний класс для построения клиента.
     */
    public static class ClientBuilder {
        private String firstName;
        private String lastName;
        private String address;
        private String passport;

        /**
         * Конструктор для ClientBuilder.
         *
         * @param firstName имя клиента
         * @param lastName фамилия клиента
         */
        public ClientBuilder(String firstName, String lastName) {
            if (firstName == null || lastName == null) {
                throw new IllegalArgumentException("Имя и фамилия обязательны.");
            }
            this.firstName = firstName;
            this.lastName = lastName;
        }

        /**
         * Устанавливает адрес клиента.
         *
         * @param address адрес клиента
         * @return текущий объект ClientBuilder
         */
        public ClientBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        /**
         * Устанавливает паспортные данные клиента.
         *
         * @param passport паспортные данные клиента
         * @return текущий объект ClientBuilder
         */
        public ClientBuilder withPassport(String passport) {
            this.passport = passport;
            return this;
        }

        /**
         * Создает объект клиента.
         *
         * @return новый объект Client
         */
        public Client build() {
            Client client = new Client(firstName, lastName);
            client.address = address;
            client.passport = passport;
            return client;
        }
    }
}