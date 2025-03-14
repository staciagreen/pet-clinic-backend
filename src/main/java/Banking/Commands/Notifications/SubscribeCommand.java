package Banking.Commands.Notifications;


import Banking.Bank;
import Banking.Client;
import Banking.Commands.Command;
import Banking.Printers.IPrinter;

public class SubscribeCommand implements Command {
    private final Client client;
    private final Bank bank;

    public SubscribeCommand(Client client, Bank bank) {
        this.client = client;
        this.bank = bank;
    }

    @Override
    public void execute(IPrinter printer) {
        bank.addObserver(client);
        printer.print("Client " + client.getName() + " subscribed to notifications from bank " + bank.getName());
    }
}