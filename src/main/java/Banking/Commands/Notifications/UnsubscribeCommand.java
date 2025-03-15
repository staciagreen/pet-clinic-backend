package Banking.Commands.Notifications;

import Banking.Bank;
import Banking.Client;
import Banking.Commands.Command;
import Banking.Printers.IPrinter;

/**
 * Команда для отписки клиента от уведомлений банка.
 */
public class UnsubscribeCommand implements Command {
    private final Client client;
    private final Bank bank;

    /**
     * Конструктор команды.
     *
     * @param client клиент, который отписывается от уведомлений
     * @param bank банк, от которого отписывается клиент
     */
    public UnsubscribeCommand(Client client, Bank bank) {
        this.client = client;
        this.bank = bank;
    }

    /**
     * Выполняет команду отписки клиента от уведомлений.
     *
     * @param printer принтер для вывода результатов выполнения команды
     */
    @Override
    public void execute(IPrinter printer) {
        bank.removeObserver(client);
        printer.print("Client " + client.getName() + " unsubscribed from notifications from bank " + bank.getName());
    }
}