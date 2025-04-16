package PetBase.controller.parsing.pet;

import PetBase.controller.commands.pet.UpdatePetCommand;
import PetBase.controller.commands.Command;
import PetBase.controller.parsing.CommandHandlerBase;
import PetBase.service.OwnerService;
import PetBase.service.PetService;

import java.util.Objects;
import java.util.Scanner;

public class UpdatePetCommandHandler extends CommandHandlerBase {
    private final PetService petService;
    private final OwnerService ownerService;
    private final Scanner scanner;

    public UpdatePetCommandHandler(PetService petService, OwnerService ownerService, Scanner scanner) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "update pet")) {
            return new UpdatePetCommand(petService, ownerService, scanner);
        }
        return super.handle(input);
    }
}
