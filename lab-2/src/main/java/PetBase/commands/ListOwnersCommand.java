package PetBase.commands;

import PetBase.service.OwnerService;

/**
 * Команда для вывода списка всех владельцев.
 */
public class ListOwnersCommand implements Command {
    private final OwnerService ownerService;

    /**
     * @param ownerService Сервис для работы с владельцами.
     */
    public ListOwnersCommand(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    /**
     * Выводит список всех владельцев.
     */
    @Override
    public void execute() {
        var owners = ownerService.getAllOwners();
        if (owners.isEmpty()) {
            System.out.println("Владельцы не найдены.");
        } else {
            owners.forEach(o -> System.out.println("ID: " + o.getId() + ", Имя: " + o.getName()));
        }
    }
}