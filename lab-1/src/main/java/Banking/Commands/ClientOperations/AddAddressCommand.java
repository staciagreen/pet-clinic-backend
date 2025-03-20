package Banking.Commands.ClientOperations;

import Banking.Client;
import Banking.Commands.Command;
import Banking.Printers.IPrinter;

/**
 * Команда для добавления или обновления адреса клиента.
 */
public class AddAddressCommand implements Command {
    private final Client client;
    private final String newAddress;

    /**
     * Конструктор команды.
     *
     * @param client клиент, для которого обновляется адрес
     * @param newAddress новый адрес
     */
    public AddAddressCommand(Client client, String newAddress) {
        this.client = client;
        this.newAddress = newAddress;
    }

    /**
     * Выполняет команду обновления адреса клиента.
     *
     * @param printer принтер для вывода результатов выполнения команды
     */
    @Override
    public void execute(IPrinter printer) {
        client.SetAddress(newAddress);
        printer.print("Address updated for client " + client.getName() + " to: " + newAddress);
    }
}