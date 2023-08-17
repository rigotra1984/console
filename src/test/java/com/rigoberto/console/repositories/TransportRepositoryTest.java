package com.rigoberto.console.repositories;

import com.rigoberto.console.entities.*;
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
public class TransportRepositoryTest {
    @Autowired
    private TransportRepository transportRepository;

    @Test
    void injectedComponentsAreNotNull(){
        Assertions.assertNotNull(transportRepository, "El repository no puede ser null");
    }

    @Test
    @DisplayName("Unit TransportRepository findAll")
    void findAll() {
        List<Transport> transports = transportRepository.findAll();

        Assertions.assertNotNull(transports, "El findAll del repository no puede ser null");
        assertEquals(5, transports.size(), "El findAll del repository debe devolver 5 elementos");

        assertEquals(0, transports.get(0).getId(), "El id del elemento1 debe ser 0");
        assertEquals("MERCEDES", transports.get(0).getBrand(), "La marca del elemento1 debe ser: \"MERCEDES\"");
        assertEquals(Destination.WALK, transports.get(0).getDestination(), "El Destino del elemento1 deber ser WALK(paseo)");
        assertEquals(TypeVehicle.LAND, transports.get(0).getTypeVehicle(), "El trasporte elemento1 es Land(terrestre)");

        assertEquals(1, transports.get(1).getId(), "El id del elemento2 debe ser 1");
        assertEquals("YUTONG", transports.get(1).getBrand(), "La marca del elemento2 debe ser: \"YUTONG\"");
        assertEquals(Destination.PASSAGE, transports.get(1).getDestination(), "El Destino del elemento2 deber ser WALK(paseo)");
        assertEquals(TypeVehicle.LAND, transports.get(1).getTypeVehicle(), "El trasporte elemento2 es Land(terrestre)");

        assertEquals(2, transports.get(2).getId(), "El id del elemento3 debe ser 2");
        assertEquals("GIRON", transports.get(2).getBrand(), "La marca del elemento3 debe ser: \"GIRON\"");
        assertEquals(Destination.PASSAGE, transports.get(2).getDestination(), "El Destino del elemento3 deber ser WALK(paseo)");
        assertEquals(TypeVehicle.LAND, transports.get(2).getTypeVehicle(), "El trasporte elemento3 es Land(terrestre)");

        assertEquals(3, transports.get(3).getId(), "El id del elemento4 debe ser 3");
        assertEquals("BOING 777", transports.get(3).getBrand(), "La marca del elemento4 debe ser: \"BOING 777\"");
        assertEquals(Destination.PASSAGE, transports.get(3).getDestination(), "El Destino del elemento4 deber ser PASSAGE(passage)");
        assertEquals(TypeVehicle.AERIAL, transports.get(3).getTypeVehicle(), "El trasporte elemento4 es AERIAL");

        assertEquals(4, transports.get(4).getId(), "El id del elemento5 debe ser 4");
        assertEquals("QUEEN OF SEA", transports.get(4).getBrand(), "La marca del elemento5 debe ser: \"QUEEN OF SEA\"");
        assertEquals(Destination.BURDEN, transports.get(4).getDestination(), "El Destino del elemento5 deber ser BURDEN(carga)");
        assertEquals(TypeVehicle.MARITIME, transports.get(4).getTypeVehicle(), "El trasporte elemento5 es MARITIME");
    }
    @Test
    @DisplayName("Unit TransportRepository findById")
    void findById() {
        Optional<Transport> transport = transportRepository.findById(1);
        Assertions.assertTrue(transport.isPresent(),"El findById 1 del repository no puede ser null");

        assertEquals(1, transport.get().getId(), "El id del elemento2 debe ser 1");
        assertEquals("YUTONG", transport.get().getBrand(), "La marca del elemento2 debe ser: \"YUTONG\"");
        assertEquals(Destination.PASSAGE, transport.get().getDestination(), "El Destino del elemento2 deber ser WALK(paseo)");
        assertEquals(TypeVehicle.LAND, transport.get().getTypeVehicle(), "El trasporte elemento2 es Land(terrestre)");

    }
    @Test
    @DisplayName("Unit TransportRepository Update")
    void update(){
        Optional<Transport> transport = transportRepository.findById(2);
        Assertions.assertTrue(transport.isPresent(),"El findById 2 del repository no puede ser null");

        Transport t = transport.get();
        t.setDestination(Destination.WALK);
        t.setBrand("HIUNDAY");

        transportRepository.save(t);

        transport = transportRepository.findById(2);
        Assertions.assertTrue(transport.isPresent(),"El findById 2 del repository no puede ser null");

        assertEquals(2, transport.get().getId(), "El id del elemento3 debe ser 2");
        assertEquals("HIUNDAY", transport.get().getBrand(), "La marca del elemento3 debe ser: \"HIUNDAY\"");
        assertEquals(Destination.WALK, transport.get().getDestination(), "El Destino del elemento3 deber ser WALK(paseo)");
        assertEquals(TypeVehicle.LAND, transport.get().getTypeVehicle(), "El trasporte elemento3 es Land(terrestre)");
    }
    @Test
    @DisplayName("Unit TransportRepository Update")
    void deleteById(){
        transportRepository.deleteById(2);

        List<Transport> transports = transportRepository.findAll();

        Assertions.assertNotNull(transports, "El findAll del repository no puede ser null");
        assertEquals(4, transports.size(), "El findAll del repository debe devolver 5 elementos");

        assertEquals(3, transports.get(2).getId(), "El id del elemento3 debe ser 3");
        assertEquals("BOING 777", transports.get(2).getBrand(), "La marca del elemento4 debe ser: \"BOING 777\"");
        assertEquals(Destination.PASSAGE, transports.get(2).getDestination(), "El Destino del elemento4 deber ser PASSAGE(passage)");
        assertEquals(TypeVehicle.AERIAL, transports.get(2).getTypeVehicle(), "El trasporte elemento4 es AERIAL");
    }

}
//(now(), 'LAND', 'WALK','MERCEDES'),
//(now(), 'LAND', 'PASSAGE','YUTONG'),
//(now(), 'LAND', 'PASSAGE','YUTONG'),
//  (now(), 'AERIAL', 'PASSAGE','BOING 777'),
//(now(), 'MARITIME', 'BURDEN','QUEEN OF SEA');