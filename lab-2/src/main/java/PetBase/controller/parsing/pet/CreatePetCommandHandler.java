package PetBase.controller.parsing.pet;

import PetBase.controller.commands.Command;
import PetBase.controller.commands.pet.CreatePetCommand;
import PetBase.controller.parsing.CommandHandlerBase;
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
        if (Objects.equals(input, "create pet")) {
            return new CreatePetCommand(petService, ownerService, scanner);
        }
        return super.handle(input);
    }
}
