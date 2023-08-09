package com.rigoberto.console.services;

import com.rigoberto.console.entities.*;
import com.rigoberto.console.repositories.DriverRepository;
import com.rigoberto.console.services.impl.DriverServiceImpl;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DriverServiceTest {
    @Mock
    private DriverRepository repository;
    private DriverService service;
    private AutoCloseable closeable;

    @BeforeEach
    void initService() {
        closeable = MockitoAnnotations.openMocks(this);
        service = new DriverServiceImpl(repository, 20);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    private static List<Driver> getTestDriverData() {
        Transport transport1 = new Transport(1, new Date(System.currentTimeMillis()), TypeVehicle.LAND, Destination.WALK, "Mercedez", new HashSet<>(), new HashSet<>());
        Transport transport2 = new Transport(2, new Date(System.currentTimeMillis()), TypeVehicle.LAND, Destination.PASSAGE, "Yutong", new HashSet<>(), new HashSet<>());
        Driver driver1 = new Driver(1, "Pedruco", "84021014200", new HashSet<>());
        Driver driver2 = new Driver(2, "Yunior", "90020218204", new HashSet<>());
        Driver driver3 = new Driver(3, "Lucio", "80060818422", new HashSet<>());

        driver1.getTransports().add(transport1);
        driver1.getTransports().add(transport2);
        driver2.getTransports().add(transport1);

        transport1.getDrivers().add(driver1);
        transport1.getDrivers().add(driver2);
        transport2.getDrivers().add(driver1);

        return List.of(
                driver1, driver2, driver3
        );
    }

    @Test
    @DisplayName("Unit /api/driver findAll")
    void getTestFindAll() {
        Mockito.when(repository.findAll()).thenReturn(getTestDriverData());

        List<Driver> result = Streams.streamOf(service.findAll()).toList();

        assertEquals(3, result.size(), "El findAll del repository debe devolver 3 elementos");
    }

    @Test
    @DisplayName("Unit /api/driver/1 findById")
    void findById() {
        Mockito.when(repository.findById(1)).thenReturn(getTestDriverData().stream().filter(x -> x.getId() == 1).findFirst());

        Optional<Driver> result = service.findById(1);

        assertTrue(result.isPresent(), "El evento con id 1 no existe");

        assertEquals(1, result.get().getId(), "El id del elemento debe ser 1");
        assertEquals("Pedruco", result.get().getName(), "La descripcion del elemento debe ser: \"Pedruco\"");
        assertEquals("84021014200", result.get().getPassport(), "La pasaporte del elemento deber ser 84021014200");
//        assertEquals(,result.get().getTransports();
    }
}
