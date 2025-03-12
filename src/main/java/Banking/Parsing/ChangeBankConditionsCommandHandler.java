package Banking.Parsing;

import Banking.Bank;
import Banking.CentralBank;
import Banking.Commands.ChangeBankConditionsCommand;
import Banking.Commands.Command;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeBankConditionsCommandHandler extends CommandHandlerBase {
    // Формат: setconditions <bankName> -interest <interestRate> -creditCommission <creditCommission>
    //          -transferLimit <transferLimit> -creditLimit <creditLimit>
    private static final Pattern COMMAND_PATTERN = Pattern.compile(
            "(?i)^setconditions\\s+(\\S+)\\s+-interest\\s+(\\S+)\\s+-creditCommission\\s+(\\S+)\\s+-transferLimit\\s+(\\S+)\\s+-creditLimit\\s+(\\S+)\\s*$"
    );

    @Override
    public Command handle(String input) {
        Matcher matcher = COMMAND_PATTERN.matcher(input);
        if (matcher.matches()) {
            String bankName = matcher.group(1);
            BigDecimal interestRate = new BigDecimal(matcher.group(2));
            BigDecimal creditCommission = new BigDecimal(matcher.group(3));
            BigDecimal transferLimit = new BigDecimal(matcher.group(4));
            BigDecimal creditLimit = new BigDecimal(matcher.group(5));

            Bank bank = CentralBank.getInstance().findBankByName(bankName);
            if (bank != null) {
                return new ChangeBankConditionsCommand(bank, interestRate, creditCommission, transferLimit, creditLimit);
            }
        }
        return super.handle(input);
    }
}

