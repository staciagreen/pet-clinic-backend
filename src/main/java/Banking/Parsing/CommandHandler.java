package Banking.Parsing;

import Banking.Commands.Command;

public interface CommandHandler {
    CommandHandler setNext(CommandHandler handler);

    Command handle(String input);
}
