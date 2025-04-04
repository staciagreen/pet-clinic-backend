package PetBase.app;

import PetBase.commands.Command;
import PetBase.parsing.Parser;
import PetBase.service.OwnerService;
import PetBase.service.PetService;

import java.util.Scanner;

/**
 * Основной класс консольного приложения для управления питомцами и их владельцами.
 * <p>
 * Приложение предоставляет интерфейс для ввода команд, которые обрабатываются парсером
 * и выполняются соответствующие действия.
 * </p>
 */
public class ConsoleApplication {

    /**
     * Точка входа в приложение.
     * <p>
     * Инициализирует необходимые сервисы и парсер, после чего запускает бесконечный цикл
     * для обработки пользовательских команд. Завершает работу при вводе команды "exit".
     * </p>
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OwnerService ownerService = new OwnerService();
        PetService petService = new PetService();

        Parser parser = new Parser(ownerService, petService, scanner);

        System.out.println("Добро пожаловать!");

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