package com.rigoberto.console.repositories;

import com.rigoberto.console.entities.*;
import org.glassfish.jaxb.core.v2.TODO;
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
public class PassengerRepositoryTest {
    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    void injectedComponentsAreNotNull() {
        Assertions.assertNotNull(passengerRepository, "El repository no puede ser null");
    }

    @Test
    @DisplayName("Unit PassengerRepository findAll")
    void findAll() {
        List<Passenger> passengers = passengerRepository.findAll();

        Assertions.assertNotNull(passengers, "El findAll del repository no puede ser null");
        assertEquals(9, passengers.size(), "El findAll del repository debe devolver 9 elementos");
//
//        assertEquals(0, passengers.get(0).getId(), "El id del elemento1 debe ser 0");
//        assertEquals("Kuko", passengers.get(0).getName(), "El nombre del pasagero1 debe ser: \"Kuko\"");
//        assertEquals(1, passengers.get(0).getTransport(), "El pasagero 1 esta en el transporte 1");
        assertEquals(0, passengers.get(0).getId(), "El id del elemento0 debe ser 0");
        assertEquals("Kuko", passengers.get(0).getName(), "El nombre de elemento0 debe ser: \"Pedro\"");
        assertEquals(0, passengers.get(0).getTransport().getId(), "El transport_id de ese elemento0 debe ser 2");
        assertEquals("Cienfuegos",passengers.get(0).getAddress().getCity(), "La ciudad de ese elemnto es Cienfuegos");
        assertEquals("Prado",passengers.get(0).getAddress().getStreet(), "En la Street San Carlos");

        assertEquals(5, passengers.get(5).getId(), "El id del elemento6 debe ser 5");
        assertEquals("Sara", passengers.get(5).getName(), "El nombre de elemento6 debe ser: \"Sara\"");
        assertEquals(3, passengers.get(5).getTransport().getId(), "El transport_id de ese elemento6 debe ser 2");
        assertEquals("La Habana",passengers.get(5).getAddress().getCity(), "La ciudad de ese elemnto6 es La Habana");
        assertEquals("Malecon",passengers.get(5).getAddress().getStreet(), "En la Street Malecon");
    }

    @Test
    @DisplayName("Unit PassengerRepository findById")
    void findById() {
        Optional<Passenger> passenger = passengerRepository.findById(1);
        Assertions.assertTrue(passenger.isPresent(),"El findById 1 del repository no puede ser null");

        assertEquals(1, passenger.get().getId(), "El id del elemento1 debe ser 1");
        assertEquals("Pedro", passenger.get().getName(), "El nombre de elemento2 debe ser: \"Pedro\"");
        assertEquals(1, passenger.get().getTransport().getId(), "El transport_id de ese elemento2 debe ser 1");
        assertEquals("Cienfuegos",passenger.get().getAddress().getCity(), "La ciudad de ese elemnto es Cienfuegos");
        assertEquals("San Carlos",passenger.get().getAddress().getStreet(), "En la Street San Carlos");

    }

    @Test
    @DisplayName("Unit PassengerRepository Update")
    void update(){
        Optional<Passenger> passenger = passengerRepository.findById(2);
        Assertions.assertTrue(passenger.isPresent(),"El findById 2 del repository no puede ser null");

        Passenger p = passenger.get();

        p.setName("Berta");
        p.getAddress().setStreet("Prado");
        p.getTransport().setId(2);
        //        p.setTransport();

        passengerRepository.save(p);

        passenger = passengerRepository.findById(2);
        Assertions.assertTrue(passenger.isPresent(),"El findById 2 del repository no puede ser null");

        assertEquals(2, passenger.get().getId(), "El id del elemento3 debe ser 2");
        assertEquals("Berta", passenger.get().getName(), "El nombre de elemento3 debe ser: \"Berta\"");
        assertEquals(2, passenger.get().getTransport().getId(), "El transport_id de ese elemento2 debe ser 1");
        assertEquals("Cienfuegos",passenger.get().getAddress().getCity(), "La ciudad de ese elemnto es Cienfuegos");
        assertEquals("Prado",passenger.get().getAddress().getStreet(), "En la Street Prado");
    }

    @Test
    @DisplayName("Unit PassengerRepository Update")
    void deleteById(){
        passengerRepository.deleteById(1);
        Optional<Passenger> passenger = passengerRepository.findById(1);
        Assertions.assertTrue(passenger.isEmpty(),"El elemnto con Id 1 del repository fue eliminado");
    }
}

//('Kuko', 0),
//('Pedro', 1),
//('Juan', 1),
//('Pito', 2),
//('Maria', 2),
//5
//('Sara', 3),

//('Rita', 3),
//('Felo', 4),
//('Lurdes', 4);

//(0, 'Cienfuegos', 'Prado'),
//(1, 'Cienfuegos', 'San Carlos'),
//(2, 'Cienfuegos', 'Santa Cruz'),
//(3, 'Cienfuegos', 'Prado'),
//(4, 'Bayamo', 'Manuel de Socorro'),
//(5, 'La Habana', 'Malecon'),
//(6, 'Matanzas', 'Los Puentes'),
//(7, 'Villa Clara', 'Vidal'),
//(8, 'Matanzas', 'La Playa');