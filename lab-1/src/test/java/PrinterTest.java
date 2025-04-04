import Banking.Printers.IPrinter;

import java.util.ArrayList;
import java.util.List;

class PrinterTest implements IPrinter {
    private final List<String> messages = new ArrayList<>();

    @Override
    public void print(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}