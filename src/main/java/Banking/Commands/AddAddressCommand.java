package Banking.Commands;

import Banking.Client;
import Banking.Printers.IPrinter;

public class AddAddressCommand implements Command {
    private final Client client;
    private final String newAddress;

    public AddAddressCommand(Client client, String newAddress) {
        this.client = client;
        this.newAddress = newAddress;
    }

    @Override
    public void execute(IPrinter printer) {
        client.SetAddress(newAddress);
        printer.print("Address updated for client " + client.getName() + " to: " + newAddress);
    }
}
