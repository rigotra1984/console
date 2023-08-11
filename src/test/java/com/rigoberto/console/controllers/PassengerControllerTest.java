package com.rigoberto.console.controllers;

import com.rigoberto.console.dtos.AddressDto;
import com.rigoberto.console.dtos.CreateDriverDto;
import com.rigoberto.console.dtos.CreatePassengerDto;
import com.rigoberto.console.entities.Address;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(value = "classpath:db/reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "classpath:db/integration_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
public class PassengerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/passenger getAll")
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/passenger")
                        //.header("Authorization", getBearer("rigo", "rigo"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(9)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Kuko"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Pedro"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Juan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].name").value("Pito"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].name").value("Maria"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[5].name").value("Sara"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[6].name").value("Rita"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[7].name").value("Felo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[8].name").value("Lurdes"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].transportId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].transportId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].transportId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].transportId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].transportId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[5].transportId").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$[6].transportId").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$[7].transportId").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[8].transportId").value(5));
    }

//    ('Kuko', 1),
//    ('Pedro', 2),
//    ('Juan', 2),
//    ('Pito', 3),
//    ('Maria', 3),
//    ('Sara', 4),
//    ('Rita', 4),
//    ('Felo', 5),
//    ('Lurdes', 5)

    @Test
    @DisplayName("GET /api/passenger/page/{page} getAllByPage")
    void getAllByPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/passenger/page/{page}", 1)
                        //.header("Authorization", getBearer("rigo", "rigo"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items", hasSize(9)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(9))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].name").value("Lurdes"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[1].name").value("Felo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[2].name").value("Rita"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[3].name").value("Sara"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[4].name").value("Maria"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[5].name").value("Pito"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[6].name").value("Juan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[7].name").value("Pedro"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[8].name").value("Kuko"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].transportId").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[1].transportId").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[2].transportId").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[3].transportId").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[4].transportId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[5].transportId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[6].transportId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[7].transportId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[8].transportId").value(1));
    }

    @Test
    @DisplayName("GET /api/passenger/{page} getById")
    void getById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/passenger/{driverId}", 1)
                        //.header("Authorization", getBearer("rigo", "rigo"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Kuko"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transportId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.street").value("Prado"))
        ;
    }

    @Test
    @DisplayName("POST /api/passenger create")
    void create() throws Exception {
        CreatePassengerDto model = new CreatePassengerDto();
        model.setName("Anita");
        model.setTransportId(2);
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/passenger")
                        //.header("Authorization", getBearer("rigo", "rigo"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(model)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Anita"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transport_id").value(2));
    }

    @Test
    @DisplayName("PUT /api/passenger/{1} update")
    void update() throws Exception {
        CreatePassengerDto model = new CreatePassengerDto();

        AddressDto addr = new AddressDto();
        addr.setStreet("PuebloGriffo");
        addr.setCity("Cienfuegos");

        model.setName("Juancin");
        model.setTransportId(3);
        model.setAddress(addr);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/passenger/{passengerId}", 1)
                        //.header("Authorization", getBearer("rigo", "rigo"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(model)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Juancin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transportId").value(3));
    }

    @Test
    @DisplayName("DELETE /api/passenger/{1} delete")
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                                .delete("/api/passenger/{passengerId}", 1)
                        //.header("Authorization", getBearer("rigo", "rigo"))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

}
