package Banking.History;

import Banking.Accounts.AccountSnapshot;
import Banking.Printers.IPrinter;

import java.util.Stack;

/**
 * Класс, представляющий историю операций.
 * Позволяет сохранять и отменять снимки состояния счетов.
 */
public class OperationHistory {
    private final Stack<AccountSnapshot> snapshots = new Stack<>();

    /**
     * Конструктор по умолчанию.
     * Создает новый экземпляр {@code OperationHistory} с пустым стеком снимков.
     */
    public OperationHistory() {
        // Default constructor
    }

    /**
     * Добавляет снимок состояния счета в историю.
     *
     * @param snapshot снимок состояния счета, который нужно добавить
     */
    public void addSnapshot(AccountSnapshot snapshot) {
        snapshots.push(snapshot);
    }

    /**
     * Отменяет последнюю операцию и возвращает снимок состояния счета.
     *
     * @return снимок состояния счета, если история не пуста, иначе {@code null}
     */
    public AccountSnapshot undo() {
        if (!snapshots.isEmpty()) {
            return snapshots.pop();
        }
        return null;
    }

    /**
     * Отображает историю операций с использованием принтера.
     *
     * @param printer принтер, используемый для вывода истории
     */
    public void showHistory(IPrinter printer) {
        printer.print("Operation History:");
        for (AccountSnapshot snapshot : snapshots) {
            printer.print("Account: " + snapshot.accountId() + ", Balance: " + snapshot.balance());
        }
    }
}