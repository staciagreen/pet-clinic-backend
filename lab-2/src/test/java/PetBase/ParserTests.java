package PetBase;

import PetBase.commands.*;
import PetBase.parsing.*;
import PetBase.service.OwnerService;
import PetBase.service.PetService;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.mockito.Mockito.*;


import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTests {

    private OwnerService ownerService;
    private PetService petService;
    private Scanner scanner;
    private Parser parser;

    @BeforeEach
    public void setUp() {
        ownerService = mock(OwnerService.class);
        petService = mock(PetService.class);

        // Просто обычный сканнер, он не используется, но нужен для конструктора
        scanner = new Scanner(System.in);

        parser = new Parser(ownerService, petService, scanner);
    }


    @Test
    public void testParseCreateOwner_returnsCorrectCommand() {
        Command command = parser.parse("create owner");
        assertNotNull(command);
        assertTrue(command instanceof CreateOwnerCommand);
    }

    @Test
    public void testParseCreatePet_returnsCorrectCommand() {
        Command command = parser.parse("create pet");
        assertNotNull(command);
        assertTrue(command instanceof CreatePetCommand);
    }

    @Test
    public void testParseListOwners_returnsCorrectCommand() {
        Command command = parser.parse("list owners");
        assertNotNull(command);
        assertTrue(command instanceof ListOwnersCommand);
    }

    @Test
    public void testParseListPets_returnsCorrectCommand() {
        Command command = parser.parse("list pets");
        assertNotNull(command);
        assertTrue(command instanceof ListPetsCommand);
    }

    @Test
    public void testParseDeletePet_returnsCorrectCommand() {
        Command command = parser.parse("delete pet");
        assertNotNull(command);
        assertTrue(command instanceof DeletePetCommand);
    }

    @Test
    public void testParseUnknownCommand_returnsNull() {
        Command command = parser.parse("abracadabra");
        assertNull(command);
    }
}

