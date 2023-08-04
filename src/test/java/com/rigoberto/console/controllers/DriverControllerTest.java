package com.rigoberto.console.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rigoberto.console.dtos.CreateEventDto;
import com.rigoberto.console.entities.Priority;
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
public class DriverControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/driver getAll")
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/driver")
                        //.header("Authorization", getBearer("rigo", "rigo"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Pepe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Juan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Siro"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].passport").value("84021014200"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].passport").value("79120215202"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].passport").value("65062215200"));
    }

    @Test
    @DisplayName("GET /api/driver/page/{page} getAllByPage")
    void getAllByPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/driver/page/{page}", 1)
                        //.header("Authorization", getBearer("rigo", "rigo"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[2].name").value("Pepe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[1].name").value("Juan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].name").value("Siro"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[2].passport").value("84021014200"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[1].passport").value("79120215202"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].passport").value("65062215200"));
    }
//
//    @Test
//    @DisplayName("GET /api/event/{page} getById")
//    void getById() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/event/{eventId}", 1)
//                        //.header("Authorization", getBearer("rigo", "rigo"))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.priority").value("MINIMUN"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("descripcion de prueba1"));
//    }
//
//    @Test
//    @DisplayName("POST /api/event create")
//    void create() throws Exception {
//        CreateDriverDto model = new CreateDriverDto();
//        model.setPriority(Priority.MAXIMUN.toString());
//        model.setDescription("prueba4");
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/driver")
//                        //.header("Authorization", getBearer("rigo", "rigo"))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(model)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(4))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.priority").value(Priority.MAXIMUN.toString()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("prueba4"));
//    }
//
//    @Test
//    @DisplayName("PUT /api/driver/{1} update")
//    void update() throws Exception {
//        CreateDriverDto model = new CreateDriverDto();
//        model.setPriority(Priority.MINIMUN.toString());
//        model.setDescription("prueba1_");
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/api/driver/{driverId}", 1)
//                        //.header("Authorization", getBearer("rigo", "rigo"))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(model)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.priority").value(Priority.MINIMUN.toString()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("prueba1_"));
//    }
//
//    @Test
//    @DisplayName("DELETE /api/driver/{1} delete")
//    void delete() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                                .delete("/api/driver/{driverId}", 1)
//                        //.header("Authorization", getBearer("rigo", "rigo"))
//                )
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
}
