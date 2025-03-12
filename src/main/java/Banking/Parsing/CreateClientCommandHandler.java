package Banking.Parsing;

import Banking.Bank;
import Banking.CentralBank;
import Banking.Commands.Command;
import Banking.Commands.CreateClientCommand;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateClientCommandHandler extends CommandHandlerBase {
    // Формат: create client <firstName> <lastName> [ -address <address text> ] [ -passport <passport text> ] <bankName>
    private static final Pattern COMMAND_PATTERN = Pattern.compile(
            "(?i)^create\\s+client\\s+(\\S+)\\s+(\\S+)" +
                    "(?:\\s+-address\\s+(.+?))?" +
                    "(?:\\s+-passport\\s+(.+?))?" +
                    "\\s+(\\S+)\\s*$"
    );

    @Override
    public Command handle(String input) {
        Matcher matcher = COMMAND_PATTERN.matcher(input);
        if (matcher.matches()) {
            String firstName = matcher.group(1);
            String lastName = matcher.group(2);
            String address = matcher.group(3);
            String passport = matcher.group(4);
            String bankName = matcher.group(5);

            Bank bank = CentralBank.getInstance().findBankByName(bankName);
            if (bank != null) {
                return new CreateClientCommand(firstName, lastName, address, passport, bank);
            }
        }
        return super.handle(input);
    }
}
