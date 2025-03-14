package Banking.Parsing;

import Banking.Commands.Command;
import Banking.Parsing.BankOperations.*;
import Banking.Parsing.ClientOperations.*;
import Banking.Parsing.Notifications.SubscribeCommandHandler;
import Banking.Parsing.Notifications.UnsubscribeCommandHandler;

public class Parser {
    private final CommandHandler handlerChain;

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

    public Command parse(String input) {
        return handlerChain.handle(input);
    }
}
