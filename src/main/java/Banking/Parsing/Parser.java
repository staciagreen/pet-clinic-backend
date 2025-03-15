package Banking.Parsing;

import Banking.Commands.Command;
import Banking.Parsing.BankOperations.*;
import Banking.Parsing.ClientOperations.*;
import Banking.Parsing.Notifications.SubscribeCommandHandler;
import Banking.Parsing.Notifications.UnsubscribeCommandHandler;

/**
 * Класс, отвечающий за разбор и обработку входящих команд.
 * Использует цепочку обязанностей (Chain of Responsibility) для обработки команд.
 */
public class Parser {
    private final CommandHandler handlerChain;

    /**
     * Конструктор класса Parser.
     * Инициализирует цепочку обработчиков команд.
     */
    public Parser() {
        // Формирование цепочки обработчиков команд
        CommandHandler withdrawHandler = new WithdrawCommandHandler();
        CommandHandler depositHandler = new DepositCommandHandler();
        CommandHandler transferHandler = new TransferCommandHandler();
        CommandHandler createAccountHandler = new CreateAccountCommandHandler();
        CommandHandler createClientHandler = new CreateClientCommandHandler();
        CommandHandler notificationHandler = new SubscribeCommandHandler();
        CommandHandler addAddressHandler = new AddAddressCommandHandler();
        CommandHandler addPassportHandler = new AddPassportCommandHandler();
        CommandHandler timeSkipHandler = new TimeSkipCommandHandler();
        CommandHandler changeBankConditions = new ChangeBankConditionsCommandHandler();
        CommandHandler unsubscribeHandler = new UnsubscribeCommandHandler();

        // Установка цепочки обработчиков
        withdrawHandler.setNext(depositHandler)
                .setNext(transferHandler)
                .setNext(createAccountHandler)
                .setNext(createClientHandler)
                .setNext(notificationHandler)
                .setNext(addAddressHandler)
                .setNext(addPassportHandler)
                .setNext(timeSkipHandler)
                .setNext(changeBankConditions)
                .setNext(unsubscribeHandler);

        this.handlerChain = withdrawHandler;
    }

    /**
     * Разбирает входящую строку и возвращает соответствующую команду.
     *
     * @param input входящая строка для разбора
     * @return объект команды, соответствующей входящей строке
     */
    public Command parse(String input) {
        return handlerChain.handle(input);
    }
}