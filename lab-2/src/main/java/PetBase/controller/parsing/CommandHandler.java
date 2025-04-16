package PetBase.controller.parsing;

import PetBase.controller.commands.Command;

public interface CommandHandler {

    CommandHandler setNext(CommandHandler next);

    Command handle(String input);
}
