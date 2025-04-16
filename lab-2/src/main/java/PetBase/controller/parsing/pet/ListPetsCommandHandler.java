package PetBase.controller.parsing.pet;

import PetBase.controller.commands.Command;
import PetBase.controller.commands.pet.ListPetsCommand;
import PetBase.controller.parsing.CommandHandlerBase;
import PetBase.service.PetService;

import java.util.Objects;

public class ListPetsCommandHandler extends CommandHandlerBase {
    private final PetService petService;

    public ListPetsCommandHandler(PetService petService) {
        this.petService = petService;
    }

    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "list pets")) {
            return new ListPetsCommand(petService);
        }
        return super.handle(input);
    }
}
