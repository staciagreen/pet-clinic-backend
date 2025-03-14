package Banking.Commands.BankOperations;

import Banking.CentralBank;
import Banking.Commands.Command;
import Banking.Printers.IPrinter;

public class TimeSkipCommand implements Command {
    private final int days;

    public TimeSkipCommand(int days) {
        this.days = days;
    }

    @Override
    public void execute(IPrinter printer) {
        CentralBank.getInstance().notifyBanksForPeriodUpdate(days);
        printer.print("Time skipped by " + days + " day(s).");
    }
}
