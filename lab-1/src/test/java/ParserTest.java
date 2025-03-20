import Banking.Commands.BankOperations.*;
import Banking.Commands.ClientOperations.AddAddressCommand;
import Banking.Commands.ClientOperations.AddPassportCommand;
import Banking.Commands.ClientOperations.CreateClientCommand;
import Banking.Commands.Notifications.UnsubscribeCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import Banking.Parsing.Parser;
import Banking.Commands.*;
import Banking.Bank;
import Banking.CentralBank;
import Banking.Client;
import Banking.Accounts.*;

import java.math.BigDecimal;
import java.util.List;

public class ParserTest {

    private Bank bank;
    private Client client;
    private PrinterTest testPrinter;
    private Parser parser;

    @BeforeEach
    public void setup() {
        CentralBank centralBank = CentralBank.getInstance();
        centralBank.clearAllBanks();
        bank = new Bank("BankA", BigDecimal.valueOf(0.03), BigDecimal.valueOf(10),
                BigDecimal.valueOf(5000), BigDecimal.valueOf(1000));
        centralBank.registerBank(bank);
        client = new Client.ClientBuilder("John", "Doe")
                .withAddress("123 Main Street")
                .withPassport("AB123456")
                .build();
        bank.addClient(client);
        testPrinter = new PrinterTest();
        parser = new Parser();
    }

    @Test
    public void test1CreateClientCommandParsing() {
        String input = "create client Alice Smith -address 456 Elm Street Apt 7 -passport PS987654 BankA";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertInstanceOf(CreateClientCommand.class, command);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.get(0).contains("Client created: Alice Smith"));
        assertTrue(messages.get(1).contains("With passport: PS987654"));
        assertTrue(messages.get(2).contains("With address: 456 Elm Street Apt 7"));
    }

    // 2. Тест парсинга команды изменения условий банка
    @Test
    public void test2ChangeBankConditionsCommandParsing() {
        String input = "setconditions BankA -interest 0.05 -creditCommission 15 -transferLimit 2000 -creditLimit 6000";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertInstanceOf(ChangeBankConditionsCommand.class, command);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.getFirst().contains("Bank conditions updated for bank BankA"));
    }

    @Test
    public void test3UnsubscribeCommandParsing() {
        Client simpleClient = new Client.ClientBuilder("JohnDoe", "Doe").build();
        bank.addClient(simpleClient);
        bank.addObserver(simpleClient); // подписываем клиента
        String input = "unsubscribe JohnDoe BankA";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertInstanceOf(UnsubscribeCommand.class, command);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.getFirst().contains("unsubscribed from notifications"));
    }

    // 4. Тест команды депозита (парсинг и выполнение)
    @Test
    public void test4DepositCommandParsingAndExecution() {
        // Создаём дебетовый счет для клиента
        DebitAccount debitAccount = new DebitAccount(client, bank);
        bank.addAccount(debitAccount);
        String input = "deposit " + debitAccount.getId().toString() + " 500";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertInstanceOf(DepositCommand.class, command);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.getFirst().contains("Deposit of 500"));
        assertEquals(BigDecimal.valueOf(500), debitAccount.getBalance());
    }

    // 5. Тест команды снятия средств
    @Test
    public void test5WithdrawCommandParsingAndExecution() {
        DebitAccount debitAccount = new DebitAccount(client, bank);
        bank.addAccount(debitAccount);
        debitAccount.deposit(BigDecimal.valueOf(1000));
        String input = "withdraw " + debitAccount.getId().toString() + " 300";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertInstanceOf(WithdrawCommand.class, command);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.getFirst().contains("Withdrawn 300"));
        assertEquals(BigDecimal.valueOf(700), debitAccount.getBalance());
    }

    // 6. Тест команды перевода между счетами
    @Test
    public void test6TransferCommandParsingAndExecution() {
        DebitAccount sender = new DebitAccount(client, bank);
        DebitAccount receiver = new DebitAccount(client, bank);
        bank.addAccount(sender);
        bank.addAccount(receiver);
        sender.deposit(BigDecimal.valueOf(1000));
        String input = "transfer " + sender.getId().toString() + " " + receiver.getId().toString() + " 400";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertInstanceOf(TransferCommand.class, command);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.getFirst().contains("Transferred 400"));
        assertEquals(BigDecimal.valueOf(600), sender.getBalance());
        assertEquals(BigDecimal.valueOf(400), receiver.getBalance());
    }

    // 7. Тест команды обновления адреса
    @Test
    public void testAddAddressCommandParsingAndExecution() {
        Client simpleClient = new Client.ClientBuilder("Mary", "Doe").build();
        bank.addClient(simpleClient);
        String input = "setaddress MaryDoe BankA UpdatedAddress";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertInstanceOf(AddAddressCommand.class, command);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.getFirst().contains("Address updated for client"));
        assertEquals("UpdatedAddress", simpleClient.getAddress());
    }

    // 8. Тест команды обновления паспорта
    @Test
    public void test8AddPassportCommandParsingAndExecution() {
        Client simpleClient = new Client.ClientBuilder("Stacia", "Doe").build();
        bank.addClient(simpleClient);
        String input = "setpassport StaciaDoe BankA NewPassport123";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertInstanceOf(AddPassportCommand.class, command);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.getFirst().contains("Passport updated for client"));
        assertEquals("NewPassport123", simpleClient.getPassport());
    }

    // 9. Тест команды ускорения времени (TimeSkip)
    @Test
    public void test9TimeSkipCommandParsingAndExecution() {
        DebitAccount debitAccount = new DebitAccount(client, bank);
        bank.addAccount(debitAccount);
        debitAccount.deposit(BigDecimal.valueOf(1000));
        String input = "time skip 5";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertInstanceOf(TimeSkipCommand.class, command);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.getFirst().contains("Time skipped by 5 day(s)."));
        BigDecimal dailyRate = BigDecimal.valueOf(0.03).divide(BigDecimal.valueOf(365), 10, BigDecimal.ROUND_HALF_UP);
        BigDecimal expectedDailyInterest = BigDecimal.valueOf(1000).multiply(dailyRate);
        BigDecimal expectedBalance = BigDecimal.valueOf(1000).add(expectedDailyInterest.multiply(BigDecimal.valueOf(5)));
        assertTrue(debitAccount.getBalance().subtract(expectedBalance).abs().compareTo(BigDecimal.valueOf(0.01)) < 0);
    }

    // 10. Тест отмены транзакции с использованием паттерна "Снимок"
    @Test
    public void test10CancelTransactionCommand() {
        DebitAccount debitAccount = new DebitAccount(client, bank);
        bank.addAccount(debitAccount);
        debitAccount.deposit(BigDecimal.valueOf(1000));
        String withdrawInput = "withdraw " + debitAccount.getId().toString() + " 200";
        Command withdrawCommand = parser.parse(withdrawInput);
        withdrawCommand.execute(testPrinter);
        BigDecimal balanceAfterWithdraw = debitAccount.getBalance();
        assertEquals(BigDecimal.valueOf(800), balanceAfterWithdraw);
        Command cancelCommand = new CancelTransactionCommand();
        cancelCommand.execute(testPrinter);
        assertEquals(BigDecimal.valueOf(1000), debitAccount.getBalance());
    }
}


