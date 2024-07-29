package com.liquibase.projectliquibase.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.liquibase.projectliquibase.model.command.create.CreatePetCommand;
import com.liquibase.projectliquibase.model.command.create.CreateToyCommand;
import com.liquibase.projectliquibase.model.command.update.UpdatePetCommand;
import com.liquibase.projectliquibase.model.command.update.UpdateToyCommand;
import com.liquibase.projectliquibase.model.dto.PetDto;
import com.liquibase.projectliquibase.model.dto.ToyDto;
import com.liquibase.projectliquibase.service.PetService;
import com.liquibase.projectliquibase.service.ToyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PetService petService;

    @Mock
    private ToyService toyService;

    @InjectMocks
    private PetController petController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testSavePet() throws Exception {
        PetDto petDto = PetDto.builder()
                .id(1)
                .name("Fluffy")
                .type("dog")
                .birthDate(LocalDate.of(2020, 1, 1))
                .build();

        CreatePetCommand command = CreatePetCommand.builder()
                .name("Fluffy")
                .type("dog")
                .birthDate(LocalDate.of(2020, 1, 1))
                .build();

        when(petService.save(any(CreatePetCommand.class))).thenReturn(petDto);

        mockMvc.perform(post("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Fluffy"));

        verify(petService, times(1)).save(any(CreatePetCommand.class));
    }

    @Test
    void testFindAllPets() throws Exception {
        PetDto petDto1 = PetDto.builder()
                .id(1)
                .name("Fluffy")
                .type("Dog")
                .build();
        PetDto petDto2 = PetDto.builder()
                .id(2)
                .name("Whiskers")
                .type("Cat")
                .build();
        List<PetDto> petList = Arrays.asList(petDto1, petDto2);
        when(petService.findAll()).thenReturn(petList);

        mockMvc.perform(get("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Fluffy"))
                .andExpect(jsonPath("$[1].name").value("Whiskers"));

        verify(petService, times(1)).findAll();
    }

    @Test
    void testFindPetById() throws Exception {
        PetDto petDto = PetDto.builder()
                .id(1)
                .name("Fluffy")
                .type("Dog")
                .build();
        when(petService.findById(anyInt())).thenReturn(petDto);

        mockMvc.perform(get("/api/v1/pets/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fluffy"));

        verify(petService, times(1)).findById(anyInt());
    }


    @Test
    void testDeletePet() throws Exception {
        doNothing().when(petService).deleteById(anyInt());

        mockMvc.perform(delete("/api/v1/pets/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(petService, times(1)).deleteById(anyInt());
    }

    @Test
    void testUpdateToyBySpecificPet() throws Exception {
        ToyDto toyDto = ToyDto.builder()
                .id(1)
                .name("Ball")
                .price(5.99)
                .petId(1)
                .build();
        when(toyService.updateBySpecificPet(anyInt(), anyInt(), any(UpdateToyCommand.class))).thenReturn(toyDto);

        mockMvc.perform(patch("/api/v1/pets/1/toys/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ball\", \"price\":5.99}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ball"));

        verify(toyService, times(1)).updateBySpecificPet(anyInt(), anyInt(), any(UpdateToyCommand.class));
    }
}
