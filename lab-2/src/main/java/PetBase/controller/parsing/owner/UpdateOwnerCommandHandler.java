package PetBase.controller.parsing.owner;

import PetBase.controller.commands.owner.UpdateOwnerCommand;
import PetBase.controller.commands.Command;
import PetBase.controller.parsing.CommandHandlerBase;
import PetBase.service.OwnerService;

import java.util.Objects;
import java.util.Scanner;


public class UpdateOwnerCommandHandler extends CommandHandlerBase {
    private final OwnerService ownerService;
    private final Scanner scanner;

    public UpdateOwnerCommandHandler(OwnerService ownerService, Scanner scanner) {
        this.ownerService = ownerService;
        this.scanner = scanner;
    }

    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "update owner")) {
            return new UpdateOwnerCommand(ownerService, scanner);
        }
        return super.handle(input);
    }
}
