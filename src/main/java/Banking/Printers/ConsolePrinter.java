package Banking.Printers;

public class ConsolePrinter implements IPrinter {
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}