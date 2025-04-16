package PetBase.controller.parsing;

import PetBase.controller.commands.Command;
import PetBase.controller.parsing.owner.CreateOwnerCommandHandler;
import PetBase.controller.parsing.owner.DeleteOwnerCommandHandler;
import PetBase.controller.parsing.owner.ListOwnersCommandHandler;
import PetBase.controller.parsing.owner.UpdateOwnerCommandHandler;
import PetBase.controller.parsing.pet.*;
import PetBase.service.OwnerService;
import PetBase.service.PetService;

import java.util.Scanner;

public class Parser {
    private final CommandHandler root;

    public Parser(OwnerService ownerService, PetService petService, Scanner scanner) {
        CommandHandler listOwners = new ListOwnersCommandHandler(ownerService);
        CommandHandler createOwner = new CreateOwnerCommandHandler(ownerService, scanner);
        CommandHandler updateOwner = new UpdateOwnerCommandHandler(ownerService, scanner);
        CommandHandler deleteOwner = new DeleteOwnerCommandHandler(ownerService, scanner);

        CommandHandler listPets = new ListPetsCommandHandler(petService);
        CommandHandler createPet = new CreatePetCommandHandler(petService, ownerService, scanner);
        CommandHandler updatePet = new UpdatePetCommandHandler(petService, ownerService, scanner);
        CommandHandler deletePet = new DeletePetCommandHandler(petService, scanner);
        CommandHandler addFriendCommandHandler = new AddFriendCommandHandler(petService, scanner);
        listOwners.setNext(createOwner)
                .setNext(updateOwner)
                .setNext(deleteOwner)
                .setNext(listPets)
                .setNext(createPet)
                .setNext(updatePet)
                .setNext(deletePet)
                .setNext(addFriendCommandHandler);

        this.root = listOwners;
    }

    public Command parse(String input) {
        return root.handle(input);
    }
}
