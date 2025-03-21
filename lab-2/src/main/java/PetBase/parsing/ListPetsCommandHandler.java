package PetBase.parsing;

import PetBase.commands.Command;
import PetBase.commands.DeletePetCommand;
import PetBase.commands.ListPetsCommand;
import PetBase.service.PetService;

import java.util.Objects;

public class ListPetsCommandHandler extends CommandHandlerBase {
    private final PetService petService;

    public ListPetsCommandHandler(PetService petService) {
        this.petService = petService;
    }

    @Override
    public Command handle(String input) {
        if (Objects.equals(input, "list pets")){
            return new ListPetsCommand(petService);
        }
        return super.handle(input);
    }
}
