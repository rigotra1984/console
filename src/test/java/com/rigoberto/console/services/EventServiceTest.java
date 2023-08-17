package com.rigoberto.console.services;

import com.rigoberto.console.entities.Event;
import com.rigoberto.console.entities.Priority;
import com.rigoberto.console.repositories.EventRepository;
import com.rigoberto.console.services.impl.EventServiceImpl;
import com.rigoberto.console.utils.Streams;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class EventServiceTest {
    @Mock
    private EventRepository repository;
    private EventService service;
    private AutoCloseable closeable;

    @BeforeEach
    void initService() {
        closeable = MockitoAnnotations.openMocks(this);
        service = new EventServiceImpl(repository, 20);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    private static List<Event> getTestEventData() {
        return List.of(
                new Event(1, new Date(System.currentTimeMillis()), Priority.MINIMUN, "descripcion de prueba1"),
                new Event(2, new Date(System.currentTimeMillis()), Priority.MEDIUM, "descripcion de prueba2"),
                new Event(3, new Date(System.currentTimeMillis()), Priority.MAXIMUN, "descripcion de prueba3"),
                new Event(4, new Date(System.currentTimeMillis()), Priority.MEDIUM, "descripcion de pruebas4" )
        );
    }

    @Test
    @DisplayName("Unit /api/event findAll")
    void getTestFindAll() {
        Mockito.when(repository.findAll()).thenReturn(getTestEventData());

        List<Event> result = Streams.streamOf(service.findAll()).toList();

        assertEquals(4, result.size(), "El findAll del repository debe devolver 4 elementos");
    }

    @Test
    @DisplayName("Unit /api/event/1 findById")
    void findById() {
        Mockito.when(repository.findById(1)).thenReturn(getTestEventData().stream().filter(x -> x.getId() == 1).findFirst());

        Optional<Event> result = service.findById(1);

        assertTrue(result.isPresent(), "El evento con id 1 no existe");

        assertEquals(1, result.get().getId(), "El id del elemento debe ser 1");
        assertEquals("descripcion de prueba1", result.get().getDescription(), "La descripcion del elemento debe ser: \"descripcion de prueba1\"");
        assertEquals(Priority.MINIMUN, result.get().getPriority(), "La prioridad del elemento deber MINIMUN");
    }
}
