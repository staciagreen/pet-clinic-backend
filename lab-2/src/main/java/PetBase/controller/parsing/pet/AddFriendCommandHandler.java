package PetBase.controller.parsing.pet;

import PetBase.controller.commands.Command;
import PetBase.controller.commands.pet.AddFriendCommand;
import PetBase.controller.parsing.CommandHandlerBase;
import PetBase.service.PetService;

import java.util.Objects;
import java.util.Scanner;

public class AddFriendCommandHandler extends CommandHandlerBase {
    private final PetService petService;
    private final Scanner scanner;

    public AddFriendCommandHandler(PetService petService, Scanner scanner) {
        this.petService = petService;
        this.scanner = scanner;
    }

    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "add friend")) {
            return new AddFriendCommand(petService, scanner);
        }
        return super.handle(input);
    }
}

