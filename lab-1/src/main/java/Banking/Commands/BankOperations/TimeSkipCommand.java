package Banking.Commands.BankOperations;

import Banking.CentralBank;
import Banking.Commands.Command;
import Banking.Printers.IPrinter;

/**
 * Команда для пропуска времени.
 */
public class TimeSkipCommand implements Command {
    private final int days;

    /**
     * Конструктор команды.
     *
     * @param days количество дней для пропуска
     */
    public TimeSkipCommand(int days) {
        this.days = days;
    }

    /**
     * Выполняет команду пропуска времени.
     *
     * @param printer принтер для вывода результатов выполнения команды
     */
    @Override
    public void execute(IPrinter printer) {
        CentralBank.getInstance().notifyBanksForPeriodUpdate(days);
        printer.print("Time skipped by " + days + " day(s).");
    }
}