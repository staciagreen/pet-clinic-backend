package Banking.Commands.Notifications;

import Banking.Bank;
import Banking.Client;
import Banking.Commands.Command;
import Banking.Printers.IPrinter;

/**
 * Команда для подписки клиента на уведомления банка.
 */
public class SubscribeCommand implements Command {
    private final Client client;
    private final Bank bank;

    /**
     * Конструктор команды.
     *
     * @param client клиент, который подписывается на уведомления
     * @param bank банк, на который подписывается клиент
     */
    public SubscribeCommand(Client client, Bank bank) {
        this.client = client;
        this.bank = bank;
    }

    /**
     * Выполняет команду подписки клиента на уведомления.
     *
     * @param printer принтер для вывода результатов выполнения команды
     */
    @Override
    public void execute(IPrinter printer) {
        bank.addObserver(client);
        printer.print("Client " + client.getName() + " subscribed to notifications from bank " + bank.getName());
    }
}