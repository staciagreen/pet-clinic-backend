package Banking.Commands.ClientOperations;

import Banking.Bank;
import Banking.Client;
import Banking.Commands.Command;
import Banking.Printers.IPrinter;

public class CreateClientCommand implements Command {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String passport;
    private final Bank bank;

    public CreateClientCommand(String firstName, String lastName, String address, String passport, Bank bank) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.passport = passport;
        this.bank = bank;
    }

    @Override
    public void execute(IPrinter printer) {
        Client client = new Client.ClientBuilder(firstName, lastName)
                .withAddress(address)
                .withPassport(passport)
                .build();

        bank.addClient(client);
        printer.print("Client created: " + firstName + " " + lastName + " in bank " + bank.getName());
        if (passport != null) {
            printer.print("With passport: " + passport);
        }
        if (address != null) {
            printer.print("With address: " + address);
        }
    }
}
