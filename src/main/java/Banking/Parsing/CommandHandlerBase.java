package Banking.Parsing;

import Banking.Commands.Command;

public abstract class CommandHandlerBase implements CommandHandler {
    private CommandHandler nextHandler;

    @Override
    public CommandHandler setNext(CommandHandler handler) {
        this.nextHandler = handler;
        return handler;
    }

    @Override
    public Command handle(String input) {
        return (nextHandler != null) ? nextHandler.handle(input) : null;
    }
}