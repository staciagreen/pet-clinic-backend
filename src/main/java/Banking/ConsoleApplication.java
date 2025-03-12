package Banking;

import Banking.Printers.ConsolePrinter;
import Banking.Printers.IPrinter;
import Banking.Parsing.Parser;
import Banking.Commands.Command;

import java.util.Scanner;

public class ConsoleApplication {
    private final CentralBank centralBank;
    private final IPrinter printer;
    private final Parser parser;

    public ConsoleApplication() {
        centralBank = CentralBank.getInstance();
        printer = new ConsolePrinter();
        parser = new Parser();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        printer.print("Welcome to the Banking Application");

        while (true) {
            printer.print("Enter command (or exit):");
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
            Command command = parser.parse(input);
            if (command != null) {
                command.execute(printer);
            } else {
                printer.print("Unknown command or error in command syntax.");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        new ConsoleApplication().run();
    }
}
