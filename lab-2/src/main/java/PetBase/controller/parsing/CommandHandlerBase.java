package PetBase.controller.parsing;

import PetBase.controller.commands.Command;


public abstract class CommandHandlerBase implements CommandHandler {

    protected CommandHandler next;

    @Override
    public CommandHandler setNext(CommandHandler next) {
        this.next = next;
        return next;
    }


    @Override
    public Command handle(String input) {
        return (next != null) ? next.handle(input) : null;
    }
}
