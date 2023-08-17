package com.rigoberto.console.repositories;

import com.rigoberto.console.entities.Event;
import com.rigoberto.console.entities.Priority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQLDB)
@DataJpaTest
@SqlGroup({
        @Sql(value = "classpath:db/reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "classpath:db/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
public class EventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;

    @Test
    void injectedComponentsAreNotNull() {
        Assertions.assertNotNull(eventRepository, "El repository no puede ser null");
    }

    @Test
    @DisplayName("Unit EventRepository findAll")
    void findAll() {
        List<Event> events = eventRepository.findAll();

        Assertions.assertNotNull(events, "El findAll del repository no puede ser null");
        assertEquals(3, events.size(), "El findAll del repository debe devolver 3 elementos");

        assertEquals(0, events.get(0).getId(), "El id del elemento1 debe ser 0");
        assertEquals("descripcion de prueba1", events.get(0).getDescription(), "La descripcion del elemento1 debe ser: \"descripcion de prueba1\"");
        assertEquals(Priority.MINIMUN, events.get(0).getPriority(), "La prioridad del elemento1 deber MINIMUN");

        assertEquals(1, events.get(1).getId(), "El id del elemento2 debe ser 1");
        assertEquals("descripcion de prueba2", events.get(1).getDescription(), "La descripcion del elemento2 debe ser: \"descripcion de prueba2\"");
        assertEquals(Priority.MEDIUM, events.get(1).getPriority(), "La prioridad del elemento2 deber MEDIUM");

        assertEquals(2, events.get(2).getId(), "El id del elemento3 debe ser 2");
        assertEquals("descripcion de prueba3", events.get(2).getDescription(), "La descripcion del elemento3 debe ser: \"descripcion de prueba3\"");
        assertEquals(Priority.MAXIMUN, events.get(2).getPriority(), "La prioridad del elemento3 deber MAXIMUN");
    }

    @Test
    @DisplayName("Unit EventRepository findById")
    void findById() {
        Optional<Event> event = eventRepository.findById(0);

        Assertions.assertTrue(event.isPresent(), "El findById 0 del repository no puede ser null");

        assertEquals(0, event.get().getId(), "El id del elemento1 debe ser 0");
        assertEquals("descripcion de prueba1", event.get().getDescription(), "La descripcion del elemento1 debe ser: \"descripcion de prueba1\"");
        assertEquals(Priority.MINIMUN, event.get().getPriority(), "La prioridad del elemento1 deber MINIMUN");
    }

    @Test
    @DisplayName("Unit EventRepository update")
    void update() {
        Optional<Event> event = eventRepository.findById(0);
        Assertions.assertTrue(event.isPresent(), "El findById 0 del repository no puede ser null");

        Event e = event.get();
        e.setPriority(Priority.MEDIUM);
        e.setDescription("descripcion de prueba11");

        eventRepository.save(e);

        event = eventRepository.findById(0);
        Assertions.assertTrue(event.isPresent(), "El findById 0 del repository no puede ser null");

        assertEquals(0, event.get().getId(), "El id del elemento1 debe ser 0");
        assertEquals("descripcion de prueba11", event.get().getDescription(), "La descripcion del elemento1 debe ser: \"descripcion de prueba11\"");
        assertEquals(Priority.MEDIUM, event.get().getPriority(), "La prioridad del elemento1 deber MEDIUM");
    }

    @Test
    @DisplayName("Unit EventRepository deleteById")
    void deleteById() {
        eventRepository.deleteById(1);
        Optional<Event> event = eventRepository.findById(1);
        Assertions.assertTrue(event.isEmpty(), "El elemnto con Id 1 del repository fue eliminado");

//        List<Event> events = eventRepository.findAll();
//
//        Assertions.assertNotNull(events, "El findAll del repository no puede ser null");
//        assertEquals(2, events.size(), "El findAll del repository debe devolver 2 elementos");
//
//        assertEquals(2, events.get(1).getId(), "El id del elemento3 debe ser 2");
//        assertEquals("descripcion de prueba3", events.get(1).getDescription(), "La descripcion del elemento3 debe ser: \"descripcion de prueba3\"");
//        assertEquals(Priority.MAXIMUN, events.get(1).getPriority(), "La prioridad del elemento3 deber MAXIMUN");
    }

}
