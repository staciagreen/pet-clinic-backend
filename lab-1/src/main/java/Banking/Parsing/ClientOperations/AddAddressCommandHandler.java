package Banking.Parsing.ClientOperations;

import Banking.Bank;
import Banking.CentralBank;
import Banking.Client;
import Banking.Commands.ClientOperations.AddAddressCommand;
import Banking.Commands.Command;
import Banking.Parsing.CommandHandlerBase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Обработчик команды добавления адреса клиента.
 * Обрабатывает команду в формате: setaddress [clientName] [bankName] [newAddress].
 */
public class AddAddressCommandHandler extends CommandHandlerBase {
    /**
     * Регулярное выражение для разбора команды добавления адреса.
     * Формат: setaddress [clientName] [bankName] [newAddress].
     */
    private static final Pattern COMMAND_PATTERN = Pattern.compile(
            "(?i)^setaddress\\s+(\\S+)\\s+(\\S+)\\s+((?:\\S+)(?:\\s+\\S+){0,9})\\s*$"
    );

    /**
     * Обрабатывает входящую строку и возвращает команду добавления адреса клиента,
     * если строка соответствует формату команды.
     *
     * @param input входящая строка для обработки
     * @return объект команды {@link AddAddressCommand}, если строка соответствует формату, иначе передает запрос следующему обработчику
     */
    @Override
    public Command handle(String input) {
        Matcher matcher = COMMAND_PATTERN.matcher(input);
        if (matcher.matches()) {
            String clientName = matcher.group(1);
            String bankName = matcher.group(2);
            String newAddress = matcher.group(3);

            Bank bank = CentralBank.getInstance().findBankByName(bankName);
            if (bank != null) {
                Client client = bank.findClientByName(clientName);
                if (client != null) {
                    return new AddAddressCommand(client, newAddress);
                }
            }
        }
        return super.handle(input);
    }
}