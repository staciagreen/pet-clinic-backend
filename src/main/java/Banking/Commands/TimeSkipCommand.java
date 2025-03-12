package Banking.Commands;

import Banking.CentralBank;
import Banking.Printers.IPrinter;

public class TimeSkipCommand implements Command {
    private final int days;

    public TimeSkipCommand(int days) {
        this.days = days;
    }

    @Override
    public void execute(IPrinter printer) {
        // Симуляция ускорения времени: уведомляем банки о необходимости начислений
        CentralBank.getInstance().notifyBanksForPeriodUpdate(days);
        printer.print("Time skipped by " + days + " day(s).");
    }
}
