package PetBase.parsing;

import PetBase.commands.Command;
import PetBase.service.OwnerService;
import PetBase.service.PetService;

import java.util.Scanner;

public class Parser {
    private final CommandHandler root;

    public Parser(OwnerService ownerService, PetService petService, Scanner scanner) {
        CommandHandler listOwners = new ListOwnersCommandHandler(ownerService);
        CommandHandler createOwner = new CreateOwnerCommandHandler(ownerService, scanner);
        CommandHandler listPets = new ListPetsCommandHandler(petService);
        CommandHandler createPet = new CreatePetCommandHandler(petService, ownerService, scanner);
        CommandHandler deletePet = new DeletePetCommandHandler(petService, scanner);

        listOwners.setNext(createOwner)
                .setNext(listPets)
                .setNext(createPet)
                .setNext(deletePet);

        this.root = listOwners;
    }

    public Command parse(String input) {
        return root.handle(input);
    }
}
