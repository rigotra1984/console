package com.rigoberto.console.repositories;

import com.rigoberto.console.entities.Passenger;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQLDB)
@DataJpaTest
@SqlGroup({
        @Sql(value = "classpath:db/resetpassenger.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "classpath:db/datapassenger.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
public class PassengerRepositoryTest {
    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    void injectedComponentsAreNotNull() {
        Assertions.assertNotNull(passengerRepository, "El repository no puede ser null");
    }

    @Test
    @DisplayName("Unit TransportRepository findAll")
    void findAll() {
        List<Passenger> passengers = passengerRepository.findAll();

        Assertions.assertNotNull(passengers, "El findAll del repository no puede ser null");
        assertEquals(9, passengers.size(), "El findAll del repository debe devolver 9 elementos");
//
//        assertEquals(0, passengers.get(0).getId(), "El id del elemento1 debe ser 0");
//        assertEquals("Kuko", passengers.get(0).getName(), "El nombre del pasagero1 debe ser: \"Kuko\"");
//        assertEquals(1, passengers.get(0).getTransport(), "El pasagero 1 esta en el transporte 1");
    }
}

//    ('Kuko', 1),
//    ('Pedro',2),
//    ('Juan',3),
//    ('Pito',3),
//    ('Maria',3),
//    ('Sara',3),
//    ('Rita',3),
//    ('Felo',3),
//    ('Lurdes',3);
