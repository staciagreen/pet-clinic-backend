package Banking.Accounts;

public abstract class AccountState {
    public static class ActiveState extends AccountState {
    }

    public static class SuspiciousState extends AccountState {
    }
}
