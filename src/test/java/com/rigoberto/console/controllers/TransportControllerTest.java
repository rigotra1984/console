package com.rigoberto.console.controllers;

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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(value = "classpath:db/reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "classpath:db/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
public class TransportControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    @DisplayName("GET /api/transport getAll")
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/transport")
                        //.header("Authorization", getBearer("rigo", "rigo"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].typeVehicle").value("LAND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].typeVehicle").value("LAND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].typeVehicle").value("LAND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].typeVehicle").value("AERIAl"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].typeVehicle").value("MARITIME"));
    }
}
