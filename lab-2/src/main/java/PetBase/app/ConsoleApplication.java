package PetBase.app;

import PetBase.commands.Command;
import PetBase.parsing.Parser;
import PetBase.service.OwnerService;
import PetBase.service.PetService;

import java.util.Scanner;

public class ConsoleApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OwnerService ownerService = new OwnerService();
        PetService petService = new PetService();

        Parser parser = new Parser(ownerService, petService, scanner);

        System.out.println("🐾 Добро пожаловать!");

        while (true) {
            System.out.print("\nВведите команду: ");
            String input = scanner.nextLine().trim();
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
            Command command = parser.parse(input);
            if (command != null) {
                command.execute();
            } else {
                System.out.println("Неизвестная команда.");
            }
        }
    }
}
