package Banking.Commands;

import Banking.Bank;
import Banking.Client;
import Banking.Printers.IPrinter;

public class UnsubscribeCommand implements Command {
    private final Client client;
    private final Bank bank;

    public UnsubscribeCommand(Client client, Bank bank) {
        this.client = client;
        this.bank = bank;
    }

    @Override
    public void execute(IPrinter printer) {
        bank.removeObserver(client);
        printer.print("Client " + client.getName() + " unsubscribed from notifications from bank " + bank.getName());
    }
}

