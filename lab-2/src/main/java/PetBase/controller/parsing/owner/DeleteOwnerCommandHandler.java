package PetBase.controller.parsing.owner;

import PetBase.controller.commands.Command;
import PetBase.controller.commands.owner.DeleteOwnerCommand;
import PetBase.controller.parsing.CommandHandlerBase;
import PetBase.service.OwnerService;

import java.util.Objects;
import java.util.Scanner;

public class DeleteOwnerCommandHandler extends CommandHandlerBase {
    private final OwnerService ownerService;
    private final Scanner scanner;

    public DeleteOwnerCommandHandler(OwnerService ownerService, Scanner scanner) {
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "delete owner")) {
            return new DeleteOwnerCommand(ownerService, scanner);
        }
        return super.handle(input);
    }
}
