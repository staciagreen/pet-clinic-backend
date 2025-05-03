package PetBase;

import PetBase.controller.PetRestController;
import PetBase.dao.entity.Owner;
import PetBase.dao.entity.Pet;
import PetBase.service.PetService;
import PetBase.service.OwnerService;
import PetBase.service.dto.PetDTO;
import PetBase.service.mapping.PetMapper;
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

@WebMvcTest(PetRestController.class)
public class PetTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @MockBean
    private OwnerService ownerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("GET /api/pets")
    class GetAllPetsTests {
        @Test
        @DisplayName("should return all pets successfully")
        void getAllPetsSuccess() throws Exception {
            PetDTO pet = new PetDTO(1L, "Барсик", "2020-05-10", "Сфинкс", "белый", "Настя", 25.0);
            when(petService.getAllPets()).thenReturn(List.of(pet));

            mockMvc.perform(get("/api/pets"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(List.of(pet))));
        }
    }

    @Nested
    @DisplayName("GET /api/pets/{id}")
    class GetPetByIdTests {
        @Test
        @DisplayName("should return pet by id")
        void getPetByIdSuccess() throws Exception {
            Pet pet = new Pet();
            pet.setId(1L);
            pet.setName("Барсик");
            pet.setBirthDate("2020-05-10");
            pet.setBreed("Сфинкс");
            pet.setColor("белый");
            pet.setTailLength(25.0);

            Owner owner = new Owner();
            owner.setName("Настя");
            pet.setOwner(owner);

            when(petService.getPetById(1L)).thenReturn(pet);

            PetDTO dto = PetMapper.toDTO(pet);

            mockMvc.perform(get("/api/pets/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(dto)));
        }

        @Test
        @DisplayName("should return not found when pet does not exist")
        void getPetByIdNotFound() throws Exception {
            when(petService.getPetById(99L)).thenReturn(null);

            mockMvc.perform(get("/api/pets/99"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("POST /api/pets")
    class CreatePetTests {

        @Test
        @DisplayName("should create pet successfully")
        void createPetSuccess() throws Exception {
            String name = "Барсик";
            String birthDate = "2020-05-10";
            String breed = "Сфинкс";
            String color = "белый";
            Double tailLength = 25.0;
            Long ownerId = 1L;

            Owner owner = new Owner();
            owner.setId(ownerId);
            owner.setName("Настя");

            Pet pet = new Pet();
            pet.setId(1L);
            pet.setName(name);
            pet.setBirthDate(birthDate);
            pet.setBreed(breed);
            pet.setColor(color);
            pet.setTailLength(tailLength);
            pet.setOwner(owner);

            PetDTO expectedDto = PetMapper.toDTO(pet);

            when(ownerService.getOwnerById(ownerId)).thenReturn(owner);
            when(petService.createPet(name, birthDate, breed, color, owner)).thenReturn(pet);

            mockMvc.perform(post("/api/pets")
                            .param("name", name)
                            .param("birthDate", birthDate)
                            .param("breed", breed)
                            .param("color", color)
                            .param("tailLength", tailLength.toString())
                            .param("ownerId", ownerId.toString()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expectedDto)));
        }
    }

    @Test
    @DisplayName("should return 404 when owner not found")
    void createPetOwnerNotFound() throws Exception {
        Long ownerId = 99L;

        when(ownerService.getOwnerById(ownerId)).thenReturn(null);

        mockMvc.perform(post("/api/pets")
                        .param("name", "Барсик")
                        .param("birthDate", "2020-05-10")
                        .param("breed", "Сфинкс")
                        .param("color", "белый")
                        .param("tailLength", "25.0")
                        .param("ownerId", ownerId.toString()))
                .andExpect(status().isNotFound());
    }

    @Nested
    @DisplayName("POST /api/pets")
    class CreatePetValidationTests {

        @Test
        @DisplayName("should return 400 when name is missing")
        void createPetMissingName() throws Exception {
            mockMvc.perform(post("/api/pets")
                            .param("birthDate", "2020-05-10")
                            .param("breed", "Сфинкс")
                            .param("color", "белый")
                            .param("tailLength", "25.0")
                            .param("ownerId", "1"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("should return 400 when tailLength is missing")
        void createPetMissingTailLength() throws Exception {
            mockMvc.perform(post("/api/pets")
                            .param("name", "Барсик")
                            .param("birthDate", "2020-05-10")
                            .param("breed", "Сфинкс")
                            .param("color", "белый")
                            .param("ownerId", "1"))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("DELETE /api/pets/{id}")
    class DeletePetTests {

        @Test
        @DisplayName("should delete pet successfully")
        void deletePetSuccess() throws Exception {
            doNothing().when(petService).deletePetById(1L);

            mockMvc.perform(delete("/api/pets/1"))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("GET /api/pets/filter")
    class FilterPetsTests {

        @Test
        @DisplayName("should return filtered pets by color")
        void filterPetsByColor() throws Exception {
            PetDTO pet = new PetDTO(1L, "Барсик", "2020-05-10", "Сфинкс", "белый", "Настя", 25.0);
            Page<PetDTO> page = new PageImpl<>(List.of(pet));

            when(petService.filterPets(eq("белый"), isNull(), isNull(), any())).thenReturn(page);

            mockMvc.perform(get("/api/pets/filter")
                            .param("color", "белый"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(page)));
        }
    }

    @Test
    @DisplayName("should return filtered pets by breed")
    void filterPetsByBreed() throws Exception {
        PetDTO pet = new PetDTO(2L, "Мурка", "2019-03-15", "Британец", "серый", "Маша", 20.0);
        Page<PetDTO> page = new PageImpl<>(List.of(pet));

        when(petService.filterPets(isNull(), eq("Британец"), isNull(), any())).thenReturn(page);

        mockMvc.perform(get("/api/pets/filter")
                        .param("breed", "Британец"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(page)));
    }

    @Test
    @DisplayName("should return filtered pets by min birth date")
    void filterPetsByMinDate() throws Exception {
        PetDTO pet = new PetDTO(3L, "Снежок", "2021-12-01", "Перс", "белый", "Кирилл", 22.5);
        Page<PetDTO> page = new PageImpl<>(List.of(pet));

        when(petService.filterPets(isNull(), isNull(), eq("2021-01-01"), any())).thenReturn(page);

        mockMvc.perform(get("/api/pets/filter")
                        .param("minDate", "2021-01-01"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(page)));
    }

    @Test
    @DisplayName("should return filtered pets by color, breed and min birth date")
    void filterPetsByAllCriteria() throws Exception {
        PetDTO pet = new PetDTO(4L, "Муся", "2022-03-10", "Сфинкс", "серый", "Лена", 18.0);
        Page<PetDTO> page = new PageImpl<>(List.of(pet));

        when(petService.filterPets(eq("серый"), eq("Сфинкс"), eq("2021-01-01"), any()))
                .thenReturn(page);

        mockMvc.perform(get("/api/pets/filter")
                        .param("color", "серый")
                        .param("breed", "Сфинкс")
                        .param("minDate", "2021-01-01"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(page)));
    }

    @Nested
    @DisplayName("Validation for POST /api/pets")
    class PetValidationTests {
        @Test
        @DisplayName("should return 400 when tailLength is missing")
        void createPetMissingTailLength() throws Exception {
            mockMvc.perform(post("/api/pets")
                            .param("name", "Барсик")
                            .param("birthDate", "2020-05-10")
                            .param("breed", "Сфинкс")
                            .param("color", "белый")
                            .param("ownerId", "1"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("should return 400 when ownerId is missing")
        void createPetMissingOwnerId() throws Exception {
            mockMvc.perform(post("/api/pets")
                            .param("name", "Барсик")
                            .param("birthDate", "2020-05-10")
                            .param("breed", "Сфинкс")
                            .param("color", "белый")
                            .param("tailLength", "25.0"))
                    .andExpect(status().isBadRequest());
        }
    }

}
