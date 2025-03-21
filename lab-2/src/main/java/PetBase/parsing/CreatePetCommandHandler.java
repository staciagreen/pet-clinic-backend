package PetBase.parsing;

import PetBase.commands.Command;
import PetBase.commands.CreateOwnerCommand;
import PetBase.commands.CreatePetCommand;
import PetBase.service.OwnerService;
import PetBase.service.PetService;

import java.util.Objects;
import java.util.Scanner;

public class CreatePetCommandHandler extends CommandHandlerBase {
    private final PetService petService;
    private final OwnerService ownerService;
    private final Scanner scanner;

    public CreatePetCommandHandler(PetService petService, OwnerService ownerService, Scanner scanner) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.scanner = scanner;
    }
    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "create pet")){
            return new CreatePetCommand(petService, ownerService, scanner);
        }
        return super.handle(input);
    }
}
