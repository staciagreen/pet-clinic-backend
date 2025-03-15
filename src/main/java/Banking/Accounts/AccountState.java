package Banking.Accounts;

/**
 * Абстрактный класс, представляющий состояние счета.
 * Состояние счета может быть активным или подозрительным.
 */
public abstract class AccountState {

    /**
     * Класс, представляющий активное состояние счета.
     * В этом состоянии счет может выполнять все операции без ограничений.
     */
    public static class ActiveState extends AccountState {
    }

    /**
     * Класс, представляющий подозрительное состояние счета.
     * В этом состоянии счет может быть ограничен в выполнении некоторых операций.
     */
    public static class SuspiciousState extends AccountState {
    }
}