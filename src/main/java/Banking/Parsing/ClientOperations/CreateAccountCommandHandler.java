package Banking.Parsing.ClientOperations;

import Banking.Bank;
import Banking.CentralBank;
import Banking.Client;
import Banking.Commands.ClientOperations.CreateAccountCommand;
import Banking.Commands.Command;
import Banking.Parsing.CommandHandlerBase;

public class CreateAccountCommandHandler extends CommandHandlerBase {
    @Override
    public Command handle(String input) {
        String[] parts = input.split(" ");
        if (parts.length >= 4 && parts[0].equals("create") && parts[1].equals("account")) {
            String clientName = parts[2];
            String accountType = parts[3];
            String bankName = parts[4];

            Bank bank = CentralBank.getInstance().findBankByName(bankName);
            if (bank == null) {
                Client client = bank.findClientByName(clientName);
                return new CreateAccountCommand(client, accountType, bank);
            }
        }
        return super.handle(input);
    }
}
