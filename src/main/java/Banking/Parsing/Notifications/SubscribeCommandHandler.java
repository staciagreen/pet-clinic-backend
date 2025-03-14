package Banking.Parsing.Notifications;

import Banking.Bank;
import Banking.CentralBank;
import Banking.Client;
import Banking.Commands.Command;
import Banking.Commands.Notifications.SubscribeCommand;
import Banking.Parsing.CommandHandlerBase;

public class SubscribeCommandHandler extends CommandHandlerBase {
    public Command handle(String input) {
        // Формат: subscribe <clientName> <bankName>
        String[] parts = input.split(" ");
        if (parts.length == 3 && parts[0].equalsIgnoreCase("subscribe")) {
            String clientName = parts[1];
            String bankName = parts[2];
            Bank bank = CentralBank.getInstance().findBankByName(bankName);
            if (bank != null) {
                Client client = bank.findClientByName(clientName);
                if (client != null) {
                    return new SubscribeCommand(client, bank);
                }
            }
        }
        return super.handle(input);
    }
}
