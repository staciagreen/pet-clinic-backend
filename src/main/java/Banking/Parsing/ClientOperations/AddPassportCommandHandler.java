package Banking.Parsing.ClientOperations;

import Banking.Bank;
import Banking.CentralBank;
import Banking.Client;
import Banking.Commands.ClientOperations.AddPassportCommand;
import Banking.Commands.Command;
import Banking.Parsing.CommandHandlerBase;

public class AddPassportCommandHandler extends CommandHandlerBase {
    @Override
    public Command handle(String input) {
        // Формат: setpassport <clientName> <bankName> <newPassport>
        String[] parts = input.split(" ", 4);
        if (parts.length >= 4 && parts[0].equalsIgnoreCase("setpassport")) {
            String clientName = parts[1];
            String bankName = parts[2];
            String newPassport = parts[3];

            Bank bank = CentralBank.getInstance().findBankByName(bankName);
            if (bank != null) {
                Client client = bank.findClientByName(clientName);
                if (client != null) {
                    return new AddPassportCommand(client, newPassport);
                }
            }
        }
        return super.handle(input);
    }
}

