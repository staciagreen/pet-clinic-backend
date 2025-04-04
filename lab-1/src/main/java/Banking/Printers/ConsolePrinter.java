package Banking.Printers;

/**
 * Реализация интерфейса IPrinter для вывода сообщений в консоль.
 */
public class ConsolePrinter implements IPrinter {
    /**
     * Выводит сообщение в консоль.
     *
     * @param message сообщение для вывода
     */
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}