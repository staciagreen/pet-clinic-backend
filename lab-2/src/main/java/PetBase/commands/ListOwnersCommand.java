package PetBase.commands;

import PetBase.service.OwnerService;

public class ListOwnersCommand implements Command {
    private final OwnerService ownerService;

    public ListOwnersCommand(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public void execute() {
        var owners = ownerService.getAllOwners();
        if (owners.isEmpty()) {
            System.out.println("Владельцы не найдены.");
        } else {
            owners.forEach(o -> System.out.println("ID: " + o.getId() + ", Имя: " + o.getName()));
        }
    }

    @Override
    public String getName() {
        return "Показать всех владельцев";
    }
}
