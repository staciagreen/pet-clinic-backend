package PetBase.parsing;

import PetBase.commands.Command;
import PetBase.commands.CreatePetCommand;
import PetBase.commands.DeletePetCommand;
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
        if (Objects.equals(input, "delete pet")){
            return new DeletePetCommand(petService, scanner);
        }
        return super.handle(input);
    }
}
