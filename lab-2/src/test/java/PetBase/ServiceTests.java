package PetBase;

import PetBase.dao.OwnerDAO;
import PetBase.dao.PetDAO;
import PetBase.entity.Owner;
import PetBase.entity.Pet;
import PetBase.service.OwnerService;
import PetBase.service.PetService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceTests {

    @Test
    void testCreateOwner_returnsOwner() {
        OwnerDAO mockDAO = mock(OwnerDAO.class);
        OwnerService service = new OwnerService(mockDAO);

        Owner saved = new Owner();
        saved.setName("Настя");

        when(mockDAO.save(any())).thenReturn(saved);

        Owner result = service.createOwner("Настя", "2004-05-11");

        assertEquals("Настя", result.getName());
        verify(mockDAO).save(any());
    }

    @Test
    void testGetAllOwners_returnsList() {
        OwnerDAO mockDAO = mock(OwnerDAO.class);
        OwnerService service = new OwnerService(mockDAO);

        when(mockDAO.getAll()).thenReturn(List.of(new Owner(), new Owner(), new Owner()));

        List<Owner> owners = service.getAllOwners();

        assertEquals(3, owners.size());
        verify(mockDAO).getAll();
    }

    @Test
    void testGetPetById_returnsPet() {
        PetDAO mockDAO = mock(PetDAO.class);
        PetService service = new PetService(mockDAO);

        Pet pet = new Pet();
        pet.setName("Барсик");
        when(mockDAO.getById(1L)).thenReturn(pet);

        Pet result = service.getPetById(1L);

        assertEquals("Барсик", result.getName());
        verify(mockDAO).getById(1L);
    }

    @Test
    void testGetAllPets_returnsList() {
        PetDAO mockDAO = mock(PetDAO.class);
        PetService service = new PetService(mockDAO);

        when(mockDAO.getAll()).thenReturn(List.of(new Pet(), new Pet()));

        List<Pet> pets = service.getAllPets();

        assertEquals(2, pets.size());
        verify(mockDAO).getAll();
    }
}
