package com.rigoberto.console.repositories;

import com.rigoberto.console.entities.Driver;
import com.rigoberto.console.entities.Event;
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
public class DriverRepositoryTest {
    @Autowired
    private DriverRepository driverRepository;

    @Test
    void injectedComponentsAreNotNull() {
        Assertions.assertNotNull(driverRepository, "El repository no puede ser null");
    }

    @Test
    @DisplayName("Unit DriverRepository findAll")
    void findAll() {
        List<Driver> drivers = driverRepository.findAll();

        Assertions.assertNotNull(drivers, "El findAll del repository no puede ser null");
        assertEquals(3, drivers.size(), "El findAll del repository debe devolver 3 elementos");

        assertEquals(0, drivers.get(0).getId(), "El id del elemento1 debe ser 0");
        assertEquals("Pepe", drivers.get(0).getName(), "La descripcion del elemento1 debe ser: \"Pepe\"");
        assertEquals("84021014200", drivers.get(0).getPassport(), "La prioridad del elemento1 deber MINIMUN");

        assertEquals(1, drivers.get(1).getId(), "El id del elemento2 debe ser 1");
        assertEquals("Juan", drivers.get(1).getName(), "La descripcion del elemento2 debe ser: \"Juan\"");
        assertEquals("79120215202", drivers.get(1).getPassport(), "La prioridad del elemento2 deber MEDIUM");

        assertEquals(2, drivers.get(2).getId(), "El id del elemento3 debe ser 2");
        assertEquals("Siro", drivers.get(2).getName(), "La descripcion del elemento3 debe ser: \"Siro\"");
        assertEquals("65062215200", drivers.get(2).getPassport(), "La prioridad del elemento3 deber MAXIMUN");
    }

    @Test
    @DisplayName("Unit DriverRepository findById")
    void findById() {
        Optional<Driver> driver = driverRepository.findById(1);

        Assertions.assertTrue(driver.isPresent(), "El findById 0 del repository no puede ser null");

        assertEquals(1, driver.get().getId(), "El id del elemento1 debe ser 0");
        assertEquals("Juan", driver.get().getName(), "La descripcion del elemento1 debe ser: \"Juan\"");
        assertEquals("79120215202", driver.get().getPassport(), "La prioridad del elemento1 deber 79120215202");
    }

    @Test
    @DisplayName("Unit DriverRepository Update")
    void update() {
        Optional<Driver> driver = driverRepository.findById(2);
        Assertions.assertTrue(driver.isPresent(), "El findById 2 del repository no puede ser null");

        Driver d = driver.get();
        d.setName("Fito");
        d.setPassport("80101010100");
        //        p.setTransport();

        driverRepository.save(d);

        driver = driverRepository.findById(2);
        Assertions.assertTrue(driver.isPresent(), "El findById 2 del repository no puede ser null");

        assertEquals(2, driver.get().getId(), "El id del elemento3 debe ser 2");
        assertEquals("Fito", driver.get().getName(), "El nombre de elemento3 debe ser: \"Fito\"");
        assertEquals("80101010100", driver.get().getPassport(), "El passaport de ese elemento2 debe ser 80101010100");
    }

    @Test
    @DisplayName("Unit DriverRepository deleteById")
    void deleteById() {
        driverRepository.deleteById(1);
        Optional<Driver> event = driverRepository.findById(1);
        Assertions.assertTrue(event.isEmpty(), "El elemnto con Id 1 del repository fue eliminado");
    }
}
