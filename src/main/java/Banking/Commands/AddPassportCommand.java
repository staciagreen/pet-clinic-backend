package Banking.Commands;

import Banking.Client;
import Banking.Printers.IPrinter;

public class AddPassportCommand implements Command {
    private final Client client;
    private final String newPassport;

    public AddPassportCommand(Client client, String newPassport) {
        this.client = client;
        this.newPassport = newPassport;
    }

    @Override
    public void execute(IPrinter printer) {
        client.SetPassport(newPassport);
        printer.print("Passport updated for client " + client.getName() + " to: " + newPassport);
    }
}
