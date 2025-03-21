package PetBase.parsing;

import PetBase.commands.Command;

public interface CommandHandler {
    CommandHandler setNext(CommandHandler next);

    Command handle(String input);
}
