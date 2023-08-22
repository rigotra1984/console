package com.rigoberto.console.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rigoberto.console.KeycloakTestContainer;
import com.rigoberto.console.dtos.CreateDriverDto;
import com.rigoberto.console.dtos.CreateTransportDto;
import com.rigoberto.console.entities.Destination;
import com.rigoberto.console.entities.TypeVehicle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(value = "classpath:db/integration_reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "classpath:db/integration_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
public class TransportControllerTest extends KeycloakTestContainer {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/transport getAll")
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/transport")
                        .header("Authorization", getBearerToken("rigo", "rigo"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].typeVehicle").value("LAND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].typeVehicle").value("LAND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].typeVehicle").value("LAND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[3].typeVehicle").value("AERIAL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[4].typeVehicle").value("MARITIME"));
    }

    @Test
    @DisplayName("GET /api/transport/page/{page} getAllByPage")
    void getAllByPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/transport/page/{page}", 1)
                        .header("Authorization", getBearerToken("rigo", "rigo"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items", hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].typeVehicle").value("MARITIME"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[1].typeVehicle").value("AERIAL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[2].typeVehicle").value("LAND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[3].typeVehicle").value("LAND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[4].typeVehicle").value("LAND"))
        ;
    }

    @Test
    @DisplayName("GET /api/transport/{page} getById")
    void getById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/transport/{transportId}", 5)
                        .header("Authorization", getBearerToken("rigo", "rigo"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.typeVehicle").value("MARITIME"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("QUEEN OF SEA"));
    }

    @Test
    @DisplayName("POST /api/transport create")
    void create() throws Exception {
        CreateTransportDto model = new CreateTransportDto();
        model.setTypeVehicle(TypeVehicle.LAND.toString());
        model.setDestination(Destination.WALK.toString());
        model.setBrand("Hiunday");
        model.setDrivers(new HashSet<>());

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/transport")
                        .header("Authorization", getBearerToken("rigo", "rigo"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(model)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.typeVehicle").value(TypeVehicle.LAND.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination").value(Destination.WALK.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Hiunday"));

    }

    @Test
    @DisplayName("DELETE /api/transport/{1} delete")
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/transport/{eventId}", 1)
                        .header("Authorization", getBearerToken("rigo", "rigo"))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /api/transport/{1} update")
    void update() throws Exception {
        CreateTransportDto model = new CreateTransportDto();

        model.setTypeVehicle(TypeVehicle.LAND.toString());
        model.setDestination(Destination.PASSAGE.toString());
        model.setBrand("MERCEDES_");
        model.setDrivers(new HashSet<>());

        model.getDrivers().add(1);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/transport/{transportId}", 1)
                        .header("Authorization", getBearerToken("rigo", "rigo"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(model)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination").value(Destination.PASSAGE.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("MERCEDES_"));
    }
}

