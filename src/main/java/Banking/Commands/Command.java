package Banking.Commands;

import Banking.Printers.IPrinter;

/**
 * Интерфейс для команд.
 * Определяет метод execute, который выполняет команду.
 */
public interface Command {
    /**
     * Выполняет команду.
     *
     * @param printer принтер для вывода результатов выполнения команды
     */
    void execute(IPrinter printer);
}