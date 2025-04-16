package PetBase.controller.parsing.owner;

import PetBase.controller.commands.Command;
import PetBase.controller.commands.owner.CreateOwnerCommand;
import PetBase.controller.parsing.CommandHandlerBase;
import PetBase.service.OwnerService;

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
        if (Objects.equals(input, "create owner")) {
            return new CreateOwnerCommand(ownerService, scanner);
        }
        return super.handle(input);
    }
}
