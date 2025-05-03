package PetBase;

import PetBase.controller.OwnerRestController;
import PetBase.dao.entity.Owner;
import PetBase.service.OwnerService;
import PetBase.service.dto.OwnerDTO;
import PetBase.service.dto.PetDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerRestController.class)
@DisplayName("Owner")
public class OwnerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("GET /api/owners")
    class GetOwnersTests {

        @Test
        @DisplayName("should return list of owners")
        void getAllOwnersSuccess() throws Exception {
            OwnerDTO owner = new OwnerDTO(1L, "Настя", "2002-03-15");
            when(ownerService.getAllOwners()).thenReturn(List.of(owner));

            mockMvc.perform(get("/api/owners"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(List.of(owner))));
        }
    }

    @Nested
    @DisplayName("GET /api/owners/{id}")
    class GetOwnerByIdTests {

        @Test
        @DisplayName("should return owner by id")
        void getOwnerByIdSuccess() throws Exception {
            OwnerDTO owner = new OwnerDTO(1L, "Настя", "2002-03-15");
            when(ownerService.getOwnerDtoById(1L)).thenReturn(owner);

            mockMvc.perform(get("/api/owners/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(owner)));
        }

        @Test
        @DisplayName("should return 404 when owner not found")
        void getOwnerByIdNotFound() throws Exception {
            when(ownerService.getOwnerDtoById(99L)).thenReturn(null);

            mockMvc.perform(get("/api/owners/99"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("PUT /api/owners/{id}")
    class UpdateOwnerTests {
        @Test
        @DisplayName("should update owner successfully")
        void updateOwnerSuccess() throws Exception {
            OwnerDTO input = new OwnerDTO(null, "UpdatedName", "1990-01-01");
            OwnerDTO returned = new OwnerDTO(1L, "UpdatedName", "1990-01-01");

            Owner mockOwner = mock(Owner.class);

            when(ownerService.getOwnerById(1L)).thenReturn(mockOwner);
            when(ownerService.updateOwner(any())).thenReturn(returned);

            mockMvc.perform(put("/api/owners/1")
                            .param("name", "UpdatedName")
                            .param("birthDate", "1990-01-01"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(returned)));
        }


        @Test
        @DisplayName("should return not found when owner does not exist")
        void updateOwnerNotFound() throws Exception {
            when(ownerService.getOwnerById(99L)).thenReturn(null);

            mockMvc.perform(put("/api/owners/99")
                            .param("name", "NonExistent")
                            .param("birthDate", "1990-01-01"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("Owner with id 99 not found"));
        }

    }

    @Nested
    @DisplayName("DELETE /api/owners/{id}")
    class DeleteOwnerTests {
        @Test
        @DisplayName("should delete owner by id successfully")
        void deleteOwnerByIdSuccess() throws Exception {
            doNothing().when(ownerService).deleteOwnerById(6L);

            mockMvc.perform(delete("/api/owners/6"))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("should return not found when deleting non-existing owner")
        void deleteOwnerByIdNotFound() throws Exception {
            doThrow(new RuntimeException("Owner with id 7 not found"))
                    .when(ownerService).deleteOwnerById(7L);

            mockMvc.perform(delete("/api/owners/7"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("Owner with id 7 not found"));
        }
    }

    @Nested
    @DisplayName("POST /api/owners")
    class CreateOwnerTests {

        @Test
        @DisplayName("should create owner successfully")
        void createOwnerSuccess() throws Exception {
            OwnerDTO input = new OwnerDTO(null, "Alice", "1990-01-01");
            OwnerDTO returned = new OwnerDTO(1L, "Alice", "1990-01-01");

            when(ownerService.createOwner("Alice", "1990-01-01")).thenReturn(returned);

            mockMvc.perform(post("/api/owners")
                            .param("name", "Alice")
                            .param("birthDate", "1990-01-01"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(returned)));
        }
    }

    @Nested
    @DisplayName("Validation errors")
    class ValidationTests {

        @Test
        @DisplayName("should fail when name is missing")
        void nameMissing() throws Exception {
            mockMvc.perform(post("/api/owners")
                            .param("birthDate", "1990-01-01"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("should fail when birthDate is missing")
        void birthDateMissing() throws Exception {
            mockMvc.perform(post("/api/owners")
                            .param("name", "Alice"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("should fail when name is blank")
        void nameBlank() throws Exception {
            mockMvc.perform(post("/api/owners")
                            .param("name", " ")
                            .param("birthDate", "1990-01-01"))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("DELETE /api/owners")
    class DeleteAllOwnersTests {
        @Test
        @DisplayName("should delete all owners successfully")
        void deleteAllOwnersSuccess() throws Exception {
            doNothing().when(ownerService).deleteAllOwners();

            mockMvc.perform(delete("/api/owners"))
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    @DisplayName("GET /api/owners/{id}/pets")
    class GetOwnerPetsTests {

        @Test
        @DisplayName("should return pets of owner")
        void getPetsOfOwnerSuccess() throws Exception {
            PetDTO pet = new PetDTO(10L, "Барсик", "2020-05-10", "Сфинкс", "белый", "Настя", 25.0);
            when(ownerService.getOwnerById(1L)).thenReturn(new Owner());
            when(ownerService.getPetsOfOwner(1L)).thenReturn(List.of(pet));

            mockMvc.perform(get("/api/owners/1/pets"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(List.of(pet))));
        }

        @Test
        @DisplayName("should return 404 when owner does not exist")
        void getPetsOfOwnerNotFound() throws Exception {
            when(ownerService.getOwnerById(99L)).thenReturn(null);

            mockMvc.perform(get("/api/owners/99/pets"))
                    .andExpect(status().isNotFound());
        }
    }


    @Nested
    @DisplayName("GET /api/owners/filter")
    class FilterOwnersTests {
        @Test
        @DisplayName("should return filtered owners by name")
        void filterOwnersByName() throws Exception {
            OwnerDTO dto = new OwnerDTO(1L, "Bob", "1985-06-15");
            Page<OwnerDTO> page = new PageImpl<>(List.of(dto));

            when(ownerService.filterOwners(eq("Bob"), isNull(), any()))
                    .thenReturn(page);

            mockMvc.perform(get("/api/owners/filter")
                            .param("name", "Bob"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(page)));
        }
    }


    @Nested
    @DisplayName("GET /api/owners/{id}/pets")
    class GetPetsOfOwnerTests {
        @Test
        @DisplayName("should return pets of owner")
        void getPetsOfOwnerSuccess() throws Exception {
            PetDTO pet = new PetDTO(10L, "Kitty", "2019-04-01", "Persian", "BLACK", "Alice", 25.0);

            when(ownerService.getOwnerById(3L)).thenReturn(new Owner());
            when(ownerService.getPetsOfOwner(3L)).thenReturn(List.of(pet));

            mockMvc.perform(get("/api/owners/3/pets"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(List.of(pet))));
        }

        @Test
        @DisplayName("should return not found when owner does not exist")
        void getPetsOfOwnerNotFound() throws Exception {
            when(ownerService.getOwnerById(99L)).thenReturn(null);

            mockMvc.perform(get("/api/owners/99/pets"))
                    .andExpect(status().isNotFound());
        }
    }

}
