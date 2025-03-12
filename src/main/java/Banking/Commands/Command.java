package Banking.Commands;

import Banking.Printers.IPrinter;

public interface Command {
    void execute(IPrinter printer);
}