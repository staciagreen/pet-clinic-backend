package PetBase.service;

import PetBase.dao.PetDAO;
import PetBase.entity.Owner;
import PetBase.entity.Pet;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PetServiceTest {

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
