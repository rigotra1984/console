package com.rigoberto.console.services;

import com.rigoberto.console.entities.*;
import com.rigoberto.console.repositories.PassengerRepository;
import com.rigoberto.console.services.impl.PassengerServiceimpl;
import com.rigoberto.console.utils.Streams;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PassengerServiceTest {
    @Mock
    private PassengerRepository repository;
    private PassengerService service;
    private AutoCloseable closeable;

    @BeforeEach
    void initService() {
        closeable = MockitoAnnotations.openMocks(this);
        service = new PassengerServiceimpl(repository, 20);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    private static List<Passenger> getTestPassengerData() {

        Transport transport1 = new Transport(1, new Date(System.currentTimeMillis()), TypeVehicle.LAND, Destination.PASSAGE, "Yutong", new HashSet<>(), new HashSet<>());
        Address address1 = new Address(1, "Prado","Cienfuegos",null);
        Passenger passenger1 = new Passenger(1, "Richard", transport1, address1);
        address1.setPassenger(passenger1);

//        new Passenger(2, "Juan", "1", "La Habana, Malecon"),
//                new Passenger(3, "Pito", "2", "Cienfuegos, Junco Sur"),
//                new Passenger(4, "Maria", "3", "Matanzas, El Puente")
        transport1.getPassengers().add(passenger1);
        return List.of(
                passenger1
        );
    }

    @Test
    @DisplayName("Unit /api/passenger findAll")
    void getTestFindAll() {
        Mockito.when(repository.findAll()).thenReturn(getTestPassengerData());

        List<Passenger> result = Streams.streamOf(service.findAll()).toList();

        assertEquals(1, result.size(), "El findAll del repository debe devolver 3 elementos");
    }
//
//    @Test
//    @DisplayName("Unit /api/passenger/1 findById")
//    void findById() {
//        Mockito.when(repository.findById(1)).thenReturn(getTestPassengerData().stream().filter(x -> x.getId() == 1).findFirst());
//
//        Optional<Passenger> result = service.findById(1);
//
//        assertTrue(result.isPresent(), "El passenger con id 1 no existe");
//
//        assertEquals(1, result.get().getId(), "El id del elemento debe ser 1");
//        assertEquals("Pedruco", result.get().getName(), "La descripcion del elemento debe ser: \"Pedruco\"");
//        assertEquals("84021014200", result.get().getPassport(), "La pasaporte del elemento deber ser 84021014200");
//    }
}
