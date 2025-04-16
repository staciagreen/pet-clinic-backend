package PetBase.controller.parsing.pet;

import PetBase.controller.commands.Command;
import PetBase.controller.commands.pet.DeletePetCommand;
import PetBase.controller.parsing.CommandHandlerBase;
import PetBase.service.PetService;

import java.util.Objects;
import java.util.Scanner;

public class DeletePetCommandHandler extends CommandHandlerBase {
    private final PetService petService;
    private final Scanner scanner;

    public DeletePetCommandHandler(PetService petService, Scanner scanner) {
        this.petService = petService;
        this.scanner = scanner;
    }

    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "delete pet")) {
            return new DeletePetCommand(petService, scanner);
        }
        return super.handle(input);
    }
}
