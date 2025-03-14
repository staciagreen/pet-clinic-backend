package Banking.Commands.ClientOperations;

import Banking.Bank;
import Banking.Accounts.Account;
import Banking.Accounts.CreditAccount;
import Banking.Accounts.DebitAccount;
import Banking.Accounts.DepositAccount;
import Banking.Client;
import Banking.Commands.Command;
import Banking.Printers.IPrinter;

public class CreateAccountCommand implements Command {
    private final Client client;
    private final String accountType;
    private final Bank bank;

    public CreateAccountCommand(Client client, String accountType, Bank bank) {
        this.client = client;
        this.accountType = accountType;
        this.bank = bank;
    }

    @Override
    public void execute(IPrinter printer) {
        Account account = null;
        if (accountType.equalsIgnoreCase("debit")) {
            account = new DebitAccount(client, bank);
        } else if (accountType.equalsIgnoreCase("deposit")) {
            account = new DepositAccount(client, bank);
        } else if (accountType.equalsIgnoreCase("credit")) {
            account = new CreditAccount(client, bank);
        }

        if (account != null) {
            bank.addAccount(account);
            printer.print("In bank " + bank.getName() + " was created " + accountType +
                    " account for client " + client.GetFullName());
        } else {
            printer.print("Error creating account for client: " + client.GetFullName());
        }
    }
}
