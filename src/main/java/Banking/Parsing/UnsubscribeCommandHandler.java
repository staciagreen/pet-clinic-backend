package Banking.Parsing;

import Banking.Bank;
import Banking.CentralBank;
import Banking.Client;
import Banking.Commands.Command;
import Banking.Commands.UnsubscribeCommand;

public class UnsubscribeCommandHandler extends CommandHandlerBase {
    // Формат: unsubscribe <clientName> <bankName>
    @Override
    public Command handle(String input) {
        String[] parts = input.split(" ");
        if (parts.length == 3 && parts[0].equalsIgnoreCase("unsubscribe")) {
            String clientName = parts[1];
            String bankName = parts[2];
            Bank bank = CentralBank.getInstance().findBankByName(bankName);
            if (bank != null) {
                Client client = bank.findClientByName(clientName);
                if (client != null) {
                    return new UnsubscribeCommand(client, bank);
                }
            }
        }
        return super.handle(input);
    }
}
