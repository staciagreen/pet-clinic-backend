package PetBase.service;

import PetBase.dao.OwnerDAO;
import PetBase.entity.Owner;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OwnerServiceTest {

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
}
