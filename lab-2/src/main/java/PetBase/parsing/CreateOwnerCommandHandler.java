package PetBase.parsing;

import PetBase.commands.Command;
import PetBase.commands.CreateOwnerCommand;
import PetBase.commands.CreatePetCommand;
import PetBase.service.OwnerService;
import PetBase.service.PetService;

import java.util.Objects;
import java.util.Scanner;

public class CreateOwnerCommandHandler extends CommandHandlerBase {
    private final OwnerService ownerService;
    private final Scanner scanner;

    public CreateOwnerCommandHandler(OwnerService ownerService, Scanner scanner) {
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "create owner")){
            return new CreateOwnerCommand(ownerService, scanner);
        }
        return super.handle(input);
    }
}
