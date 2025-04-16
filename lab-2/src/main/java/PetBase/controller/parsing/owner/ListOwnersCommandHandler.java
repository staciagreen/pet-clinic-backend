package PetBase.controller.parsing.owner;

import PetBase.controller.commands.Command;
import PetBase.controller.commands.owner.ListOwnersCommand;
import PetBase.controller.parsing.CommandHandlerBase;
import PetBase.service.OwnerService;

import java.util.Objects;

public class ListOwnersCommandHandler extends CommandHandlerBase {
    private final OwnerService ownerService;

    public ListOwnersCommandHandler(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "list owners")) {
            return new ListOwnersCommand(ownerService);
        }
        return super.handle(input);
    }
}
