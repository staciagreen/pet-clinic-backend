package Banking.Commands.BankOperations;

import Banking.Bank;
import Banking.Commands.Command;
import Banking.Printers.IPrinter;

import java.math.BigDecimal;

public class ChangeBankConditionsCommand implements Command {
    private final Bank bank;
    private final BigDecimal newInterestRate;
    private final BigDecimal newCreditCommission;
    private final BigDecimal newTransferLimit;
    private final BigDecimal newCreditLimit;

    public ChangeBankConditionsCommand(Bank bank, BigDecimal newInterestRate, BigDecimal newCreditCommission,
                                       BigDecimal newTransferLimit, BigDecimal newCreditLimit) {
        this.bank = bank;
        this.newInterestRate = newInterestRate;
        this.newCreditCommission = newCreditCommission;
        this.newTransferLimit = newTransferLimit;
        this.newCreditLimit = newCreditLimit;
    }

    @Override
    public void execute(IPrinter printer) {
        bank.setInterestRate(newInterestRate);
        bank.setCreditCommission(newCreditCommission);
        bank.setTransferLimit(newTransferLimit);
        bank.setCreditLimit(newCreditLimit);
        printer.print("Bank conditions updated for bank " + bank.getName() +
                ": interest rate = " + newInterestRate +
                ", credit commission = " + newCreditCommission +
                ", transfer limit = " + newTransferLimit +
                ", credit limit = " + newCreditLimit);
    }
}
