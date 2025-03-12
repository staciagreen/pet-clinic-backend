package Banking.Parsing;

import Banking.Commands.TimeSkipCommand;
import Banking.Commands.Command;

public class TimeSkipCommandHandler extends CommandHandlerBase {
    @Override
    public Command handle(String input) {
        // Формат: time skip <days>
        String[] parts = input.split(" ");
        if (parts.length == 3 && parts[0].equalsIgnoreCase("time") && parts[1].equalsIgnoreCase("skip")) {
            try {
                int daysToSkip = Integer.parseInt(parts[2]);
                return new TimeSkipCommand(daysToSkip);
            } catch (NumberFormatException e) {
                return super.handle(input);
            }
        }
        return super.handle(input);
    }
}
