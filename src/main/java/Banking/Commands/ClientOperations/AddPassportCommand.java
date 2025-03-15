package Banking.Commands.ClientOperations;

import Banking.Client;
import Banking.Commands.Command;
import Banking.Printers.IPrinter;

/**
 * Команда для добавления или обновления паспортных данных клиента.
 */
public class AddPassportCommand implements Command {
    private final Client client;
    private final String newPassport;

    /**
     * Конструктор команды.
     *
     * @param client клиент, для которого обновляются паспортные данные
     * @param newPassport новые паспортные данные
     */
    public AddPassportCommand(Client client, String newPassport) {
        this.client = client;
        this.newPassport = newPassport;
    }

    /**
     * Выполняет команду обновления паспортных данных клиента.
     *
     * @param printer принтер для вывода результатов выполнения команды
     */
    @Override
    public void execute(IPrinter printer) {
        client.SetPassport(newPassport);
        printer.print("Passport updated for client " + client.getName() + " to: " + newPassport);
    }
}