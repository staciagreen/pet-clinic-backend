package Banking;

import Banking.Accounts.Account;
import Banking.Observer.ObservableEntity;
import Banking.Observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public void addAccount(Account account) {
        if (!accounts.contains(account)) {
            accounts.add(account);
        }
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String GetFullName() {
        return firstName + " " + lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPassport() {
        return passport;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public void SetAddress(String address) {
        this.address = address;
        notifyObservers("Address updated");
    }

    public void SetPassport(String passport) {
        this.passport = passport;
        notifyObservers("Passport updated");
    }

    public boolean hasFullInfo() {
        return address != null && passport != null;
    }

    @Override
    public void update(String message) {
        // Реакция на уведомления
    }

    public static class ClientBuilder {
        private String firstName;
        private String lastName;
        private String address;
        private String passport;

        public ClientBuilder(String firstName, String lastName) {
            if (firstName == null || lastName == null) {
                throw new IllegalArgumentException("Имя и фамилия обязательны.");
            }
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public ClientBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public ClientBuilder withPassport(String passport) {
            this.passport = passport;
            return this;
        }

        public Client build() {
            Client client = new Client(firstName, lastName);
            client.address = address;
            client.passport = passport;
            return client;
        }
    }
}
