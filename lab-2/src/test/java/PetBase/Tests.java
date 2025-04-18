package PetBase;

import PetBase.controller.commands.pet.CreatePetCommand;
import PetBase.controller.commands.pet.UpdatePetCommand;
import PetBase.dao.entity.Owner;
import PetBase.dao.entity.Pet;
import PetBase.service.OwnerService;
import PetBase.service.PetService;
import PetBase.service.dto.OwnerDTO;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class Tests {

    @Test // Проверяет, что метод createOwner создаёт владельца и возвращает правильный DTO
    void test_ownerservice_create_returns_entity() {
        OwnerDAO mockDAO = mock(OwnerDAO.class);
        OwnerService service = new OwnerService(mockDAO);

        Owner o = new Owner();
        o.setId(1L);
        o.setName("Настя");
        o.setBirthDate("2004-05-11");

        when(mockDAO.save(any())).thenReturn(o);

        var result = service.createOwner("Настя", "2004-05-11");

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Настя", result.name());
        assertEquals("2004-05-11", result.birthDate());
        verify(mockDAO).save(any());
    }

    @Test // Проверяет, что updatePet вызывает DAO и возвращает обновлённую сущность Pet
    void test_petservice_update_invokes_dao_and_returns_updated_pet() {
        PetDAO mockDAO = mock(PetDAO.class);
        PetService service = new PetService(mockDAO);
        Pet pet = new Pet();
        pet.setName("Барсик");
        pet.setId(1L);

        when(mockDAO.update(pet)).thenReturn(pet);

        Pet updatedPet = service.updatePet(pet);

        assertNotNull(updatedPet);
        assertEquals("Барсик", updatedPet.getName());
        verify(mockDAO).update(pet);
    }

    @Test // Проверяет, что deletePetById вызывает deleteById у DAO
    void test_petservice_deleteById_invokes_dao() {
        PetDAO mockDAO = mock(PetDAO.class);
        PetService service = new PetService(mockDAO);

        service.deletePetById(1L);

        verify(mockDAO).deleteById(1L);
    }

    @Test // Проверяет корректное выполнение команды создания питомца с моканным вводом
    void test_ownerservice_getById_returns_correct_dto() {
        OwnerDAO mockDAO = mock(OwnerDAO.class);
        OwnerService service = new OwnerService(mockDAO);

        Owner owner = new Owner();
        owner.setId(2L);
        owner.setName("Миша");
        owner.setBirthDate("2005-10-15");

        when(mockDAO.getById(2L)).thenReturn(owner);

        OwnerDTO dto = service.getOwnerDtoById(2L);

        assertNotNull(dto);
        assertEquals(2L, dto.id());
        assertEquals("Миша", dto.name());
        assertEquals("2005-10-15", dto.birthDate());
        verify(mockDAO).getById(2L);
    }

    @Test // Проверяет, что getAllPets возвращает список DTO с корректными данными
    void test_petservice_getAllPets_returns_list_of_dtos() {
        PetDAO mockDAO = mock(PetDAO.class);
        PetService service = new PetService(mockDAO);

        Pet pet1 = new Pet();
        pet1.setId(1);
        pet1.setName("Барсик");
        pet1.setBirthDate("2020-01-01");
        pet1.setBreed("Сиам");
        pet1.setColor("серый");
        Owner owner = new Owner();
        owner.setName("Настя");
        pet1.setOwner(owner);

        Pet pet2 = new Pet();
        pet2.setId(2);
        pet2.setName("Мурзик");
        pet2.setBirthDate("2021-02-02");
        pet2.setBreed("Двортерьер");
        pet2.setColor("рыжий");
        pet2.setOwner(owner);

        when(mockDAO.getAll()).thenReturn(List.of(pet1, pet2));

        var dtos = service.getAllPets();

        assertEquals(2, dtos.size());
        assertEquals("Барсик", dtos.get(0).name());
        assertEquals("Мурзик", dtos.get(1).name());
    }

    @Test // Проверяет, что getPetById возвращает нужную сущность Pet
    void test_petservice_getPetById_returns_pet() {
        PetDAO mockDAO = mock(PetDAO.class);
        PetService service = new PetService(mockDAO);
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Барсик");

        when(mockDAO.getById(1L)).thenReturn(pet);

        Pet result = service.getPetById(1L);

        assertNotNull(result);
        assertEquals("Барсик", result.getName());
    }

    @Test // Проверяет, что getAllOwners возвращает список OwnerDTO с корректными данными
    void test_ownerservice_getAll_returns_list_of_dtos() {
        OwnerDAO mockDAO = mock(OwnerDAO.class);
        OwnerService service = new OwnerService(mockDAO);

        Owner o1 = new Owner();
        o1.setId(1L);
        o1.setName("Настя");
        o1.setBirthDate("2004-05-11");

        Owner o2 = new Owner();
        o2.setId(2L);
        o2.setName("Миша");
        o2.setBirthDate("2005-10-15");

        when(mockDAO.getAll()).thenReturn(List.of(o1, o2));

        var dtos = service.getAllOwners();

        assertEquals(2, dtos.size());
        assertEquals("Настя", dtos.get(0).name());
        assertEquals("Миша", dtos.get(1).name());
    }

    @Test // Проверяет, что updateOwner обновляет владельца и возвращает правильный DTO
    void test_ownerservice_update_updates_entity() {
        OwnerDAO mockDAO = mock(OwnerDAO.class);
        OwnerService service = new OwnerService(mockDAO);
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setName("Настя");

        when(mockDAO.update(owner)).thenReturn(owner);

        OwnerDTO result = service.updateOwner(owner);

        assertNotNull(result);
        assertEquals("Настя", result.name());
        verify(mockDAO).update(owner);
    }

    @Test
    void test_add_friend_adds_pet_to_friends_list() {
        PetDAO mockDAO = mock(PetDAO.class);
        PetService service = new PetService(mockDAO);

        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setName("Барсик");
        pet1.setFriends(new HashSet<>());

        Pet pet2 = new Pet();
        pet2.setId(2L);
        pet2.setName("Мурзик");

        when(mockDAO.getById(1L)).thenReturn(pet1);
        when(mockDAO.getById(2L)).thenReturn(pet2);

        pet1.getFriends().add(pet2);

        assertTrue(pet1.getFriends().contains(pet2));
    }

    @Test
    void test_pet_friends_list_can_be_empty() {
        Pet pet = new Pet();
        pet.setId(3);
        pet.setName("Одинокий котик");
        pet.setFriends(new HashSet<>());

        assertNotNull(pet.getFriends());
        assertTrue(pet.getFriends().isEmpty());
    }

    @Test
    void test_addFriendship_whenFriendDoesNotExist_shouldDoNothing() {
        PetDAO mockDAO = mock(PetDAO.class);
        PetService service = new PetService(mockDAO);

        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Барсик");
        pet.setFriends(new HashSet<>());

        when(mockDAO.getById(1L)).thenReturn(pet);
        when(mockDAO.getById(99L)).thenReturn(null);

        assertDoesNotThrow(() -> service.addFriendship(1L, 99L));

        assertTrue(pet.getFriends().isEmpty());
    }

    @Test
    void test_update_pet_command_executes_correctly() {
        PetService service = mock(PetService.class);
        OwnerService ownerService = mock(OwnerService.class);
        Scanner scanner = new Scanner("1\nБарсик\n01.01.2020\nСиам\nсерый\n1\n");

        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Барсик");
        pet.setBreed("Сиам");

        Owner owner = new Owner();
        owner.setId(1L);
        pet.setOwner(owner);

        when(service.getPetById(1L)).thenReturn(pet);
        when(ownerService.getOwnerById(1L)).thenReturn(owner);

        new UpdatePetCommand(service, ownerService, scanner).execute();

        verify(service).updatePet(any());
    }


    @Test
    void test_create_pet_command_executes_correctly() {
        PetService service = mock(PetService.class);
        OwnerService ownerService = mock(OwnerService.class);
        Scanner scanner = new Scanner("Мурзик\n01.01.2020\nдвортерьер\nрыжий\n1\n");
        when(ownerService.getOwnerById(1L)).thenReturn(new Owner());
        new CreatePetCommand(service, ownerService, scanner).execute();
        verify(service).createPet(eq("Мурзик"), eq("01.01.2020"), eq("двортерьер"), eq("рыжий"), any());
    }
}
