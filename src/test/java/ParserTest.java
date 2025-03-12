import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import Banking.Parsing.Parser;
import Banking.Commands.*;
import Banking.Bank;
import Banking.CentralBank;
import Banking.Client;
import Banking.Accounts.*;
import Banking.History.OperationHistory;
import Banking.Printers.IPrinter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;o
import java.util.ArrayList;

public ParserTest {

    private Bank bank;
    private Client client;
    private CentralBank centralBank;
    private TestPrinter testPrinter;
    private Parser parser;

    @BeforeEach
    public void setup () {
        // Получаем экземпляр Центрального банка
        centralBank = CentralBank.getInstance();
        // Для тестирования желательно очистить списки банков и счетов (если такой функционал отсутствует – убедитесь, что тесты выполняются на чистом экземпляре)
        // Создаём банк
        bank = new Bank("BankA", BigDecimal.valueOf(0.03), BigDecimal.valueOf(10),
                BigDecimal.valueOf(5000), BigDecimal.valueOf(1000));
        centralBank.registerBank(bank);
        // Создаём клиента (для большинства тестов)
        client = new Client.ClientBuilder("John", "Doe")
                .withAddress("123 Main Street")
                .withPassport("AB123456")
                .build();
        bank.addClient(client);
        // Инициализируем тестовый принтер и парсер
        testPrinter = new TestPrinter();
        parser = new Parser();
    }

    // 1. Тест парсинга команды создания клиента через Matcher,
    //    где адрес и паспорт располагаются в конце команды.
    @Test
    public void testCreateClientCommandParsing () {
        String input = "create client Alice Smith -address 456 Elm Street Apt 7 -passport PS987654 BankA";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertTrue(command instanceof CreateClientCommand);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        // Проверяем, что вывод содержит информацию о созданном клиенте и указанных параметрах
        assertTrue(messages.get(0).contains("Client created: Alice Smith"));
        assertTrue(messages.get(1).contains("With passport: PS987654"));
        assertTrue(messages.get(2).contains("With address: 456 Elm Street Apt 7"));
    }

    // 2. Тест парсинга команды изменения условий банка
    @Test
    public void testChangeBankConditionsCommandParsing () {
        String input = "setconditions BankA -interest 0.05 -creditCommission 15 -transferLimit 2000 -creditLimit 6000";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertTrue(command instanceof ChangeBankConditionsCommand);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.get(0).contains("Bank conditions updated for bank BankA"));
    }

    // 3. Тест парсинга команды отписки от уведомлений
    @Test
    public void testUnsubscribeCommandParsing () {
        // Для теста создаём клиента с односоставным именем (так как хендлер разделяет по пробелу)
        Client simpleClient = new Client.ClientBuilder("JohnDoe", "Doe").build();
        bank.addClient(simpleClient);
        bank.addObserver(simpleClient); // подписываем клиента
        String input = "unsubscribe JohnDoe BankA";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertTrue(command instanceof UnsubscribeCommand);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.get(0).contains("unsubscribed from notifications"));
    }

    // 4. Тест команды депозита (парсинг и выполнение)
    @Test
    public void testDepositCommandParsingAndExecution () {
        // Создаём дебетовый счет для клиента
        DebitAccount debitAccount = new DebitAccount(client, bank);
        bank.addAccount(debitAccount);
        String input = "deposit " + debitAccount.getId().toString() + " 500";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertTrue(command instanceof DepositCommand);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.get(0).contains("Deposit of 500"));
        assertEquals(BigDecimal.valueOf(500), debitAccount.getBalance());
    }

    // 5. Тест команды снятия средств
    @Test
    public void testWithdrawCommandParsingAndExecution () {
        DebitAccount debitAccount = new DebitAccount(client, bank);
        bank.addAccount(debitAccount);
        // Предварительно пополняем счет
        debitAccount.deposit(BigDecimal.valueOf(1000));
        String input = "withdraw " + debitAccount.getId().toString() + " 300";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertTrue(command instanceof WithdrawCommand);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.get(0).contains("Withdrawn 300"));
        assertEquals(BigDecimal.valueOf(700), debitAccount.getBalance());
    }

    // 6. Тест команды перевода между счетами
    @Test
    public void testTransferCommandParsingAndExecution () {
        DebitAccount sender = new DebitAccount(client, bank);
        DebitAccount receiver = new DebitAccount(client, bank);
        bank.addAccount(sender);
        bank.addAccount(receiver);
        sender.deposit(BigDecimal.valueOf(1000));
        String input = "transfer " + sender.getId().toString() + " " + receiver.getId().toString() + " 400";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertTrue(command instanceof TransferCommand);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.get(0).contains("Transferred 400"));
        assertEquals(BigDecimal.valueOf(600), sender.getBalance());
        assertEquals(BigDecimal.valueOf(400), receiver.getBalance());
    }

    // 7. Тест команды обновления адреса
    @Test
    public void testAddAddressCommandParsingAndExecution () {
        // Для теста создаём клиента с односоставным именем (требование хендлера)
        Client simpleClient = new Client.ClientBuilder("JohnDoe", "Doe").build();
        bank.addClient(simpleClient);
        String input = "setaddress JohnDoe BankA UpdatedAddress";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertTrue(command instanceof AddAddressCommand);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.get(0).contains("Address updated for client"));
        assertEquals("UpdatedAddress", simpleClient.getAddress());
    }

    // 8. Тест команды обновления паспорта
    @Test
    public void testAddPassportCommandParsingAndExecution () {
        Client simpleClient = new Client.ClientBuilder("JohnDoe", "Doe").build();
        bank.addClient(simpleClient);
        String input = "setpassport JohnDoe BankA NewPassport123";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertTrue(command instanceof AddPassportCommand);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.get(0).contains("Passport updated for client"));
        assertEquals("NewPassport123", simpleClient.getPassport());
    }

// 9. Тест команды ускорения времени (TimeSkip)
    @Test
    public void testTimeSkipCommandParsingAndExecution () {
        DebitAccount debitAccount = new DebitAccount(client, bank);
        bank.addAccount(debitAccount);
        debitAccount.deposit(BigDecimal.valueOf(1000));
        String input = "time skip 5";
        Command command = parser.parse(input);
        assertNotNull(command);
        assertTrue(command instanceof TimeSkipCommand);
        command.execute(testPrinter);
        List<String> messages = testPrinter.getMessages();
        assertTrue(messages.get(0).contains("Time skipped by 5 day(s)."));
        // Проверяем, что за 5 дней начислены проценты (с допуском на округление)
        BigDecimal dailyRate = BigDecimal.valueOf(0.03).divide(BigDecimal.valueOf(365), 10, BigDecimal.ROUND_HALF_UP);
        BigDecimal expectedDailyInterest = BigDecimal.valueOf(1000).multiply(dailyRate);
        BigDecimal expectedBalance = BigDecimal.valueOf(1000).add(expectedDailyInterest.multiply(BigDecimal.valueOf(5)));
        assertTrue(debitAccount.getBalance().subtract(expectedBalance).abs().compareTo(BigDecimal.valueOf(0.01)) < 0);
    }

    // 10. Тест отмены транзакции с использованием паттерна "Снимок"
    @Test
    public void testCancelTransactionCommand () {
        DebitAccount debitAccount = new DebitAccount(client, bank);
        bank.addAccount(debitAccount);
        debitAccount.deposit(BigDecimal.valueOf(1000));
        // Выполняем команду снятия с сохранением снимка
        String withdrawInput = "withdraw " + debitAccount.getId().toString() + " 200";
        Command withdrawCommand = parser.parse(withdrawInput);
        withdrawCommand.execute(testPrinter);
        BigDecimal balanceAfterWithdraw = debitAccount.getBalance();
        assertEquals(BigDecimal.valueOf(800), balanceAfterWithdraw);
        // Выполняем команду отмены последней операции
        Command cancelCommand = new CancelTransactionCommand();
        cancelCommand.execute(testPrinter);
        // После отмены баланс должен восстановиться до 1000
        assertEquals(BigDecimal.valueOf(1000), debitAccount.getBalance());
    }
}

// Простой тестовый принтер для сбора выводимых сообщений
class TestPrinter implements IPrinter {
    private final List<String> messages = new ArrayList<>();

    @Override
    public void print(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}