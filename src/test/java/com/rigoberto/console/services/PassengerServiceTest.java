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

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
//    dada la relacion entre clases del ejemplo, para generear una lista de pasageros completas
//        es necesario Crear los trasportes, crear las adres para cada pasagero, crear el pasagero
//        y luego cambia la propiedad de adrress de cada pasagero y por ultimo y para cerrar el ciclo de relaciones se debe asiganr el pasagero
//        a la lista de su trasoprte respectivo

        Transport transport1 = new Transport(1, new Date(System.currentTimeMillis()), TypeVehicle.LAND, Destination.PASSAGE, "Yutong", new HashSet<>(), new HashSet<>());
        Transport transport2 = new Transport(2, new Date(System.currentTimeMillis()), TypeVehicle.AERIAL, Destination.PASSAGE, "Boing", new HashSet<>(), new HashSet<>());
        Transport transport3 = new Transport(3, new Date(System.currentTimeMillis()), TypeVehicle.MARITIME, Destination.BURDEN, "CarnivalCruise", new HashSet<>(), new HashSet<>());

        Address address1 = new Address(1, "Prado","Cienfuegos",null);
        Address address2 = new Address(2, "Santa Cruz","Cienfuegos",null);
        Address address3 = new Address(3, "Gloria","Havana",null);
        Address address4 = new Address(4, "Junco Sur","Havana",null);

        Passenger passenger1 = new Passenger(1, "Richard", transport1, address1);
        address1.setPassenger(passenger1);
        Passenger passenger2 = new Passenger(2, "Ana", transport2, address2);
        address2.setPassenger(passenger2);
        Passenger passenger3 = new Passenger(3, "Felo", transport3, address3);
        address3.setPassenger(passenger3);
        Passenger passenger4 = new Passenger(4, "Juana", transport1, address4);
        address4.setPassenger(passenger4);

        transport1.getPassengers().add(passenger1);
        transport1.getPassengers().add(passenger4);
        transport2.getPassengers().add(passenger2);
        transport3.getPassengers().add(passenger3);

        return List.of(
                passenger1,passenger2,passenger3,passenger4
        );
    }

    @Test
    @DisplayName("Unit /api/passenger findAll")
    void getTestFindAll() {
        Mockito.when(repository.findAll()).thenReturn(getTestPassengerData());

        List<Passenger> result = Streams.streamOf(service.findAll()).toList();

        assertEquals(4, result.size(), "El findAll del repository debe devolver 3 elementos");
    }
//
    @Test
    @DisplayName("Unit /api/passenger/1 findById")
    void findById() {
        Mockito.when(repository.findById(1)).thenReturn(getTestPassengerData().stream().filter(x -> x.getId() == 1).findFirst());

        Optional<Passenger> result = service.findById(1);

        assertTrue(result.isPresent(), "El passenger con id 1 no existe");

        assertEquals(1, result.get().getId(), "El id del elemento debe ser 1");
        assertEquals("Richard", result.get().getName(), "La descripcion del elemento debe ser: \"Richard\"");
        assertEquals("Prado", result.get().getAddress().getStreet(), "La calle de elemento deber ser Prado");
        assertEquals(1,result.get().getTransport().getId(), "El pasagero en estudio esta asociado al transporte1");
        assertEquals(TypeVehicle.LAND,result.get().getTransport().getTypeVehicle(), "El pasagero en estudio esta asociado al type transport LAND");
    }

    @Test
    @DisplayName("Unit /api/passenger/1 deleteById")
    void deleteById() {
        int idToDelete = 1;
        Mockito.doNothing().when(repository).deleteById(idToDelete);

        service.deleteById(idToDelete);
        verify(repository, times(1)).deleteById(idToDelete);
    }

    @Test
    @DisplayName("Unit /api/transport/testSaveTransport")
    void save() {
        Transport transport = new Transport(1, new Date(System.currentTimeMillis()), TypeVehicle.LAND, Destination.PASSAGE, "Yutong", new HashSet<>(), new HashSet<>());

        Address address = new Address(5, "Presidentes","Ciego de Avila",null);

        Passenger passenger = new Passenger(5, "Yaser", transport, address);
        address.setPassenger(passenger);

        // Configura el comportamiento simulado para el método save del repositorio
        Mockito.when(repository.save(passenger)).thenReturn(passenger);

        // Llama al método save del servicio (que internamente utiliza el método save del repositorio)
        Passenger savedPassenger = service.save(passenger);

        // Verifica que el método save del repositorio se haya llamado exactamente una vez con el objeto transport
        verify(repository, times(1)).save(passenger);

        // Verifica que los datos del objeto guardado sean los esperados
        assertEquals(5, savedPassenger.getId(), "El id del objeto guardado debe ser 5");
        assertEquals("Yaser", savedPassenger.getName(), "El nombre del Passager save es \"Yaser\"");
        assertEquals("Presidentes", savedPassenger.getAddress().getStreet(), "La calle de elemento deber ser Presidentes");
        assertEquals(1,savedPassenger.getTransport().getId(), "El pasagero en estudio esta asociado al transporte1");
        assertEquals(TypeVehicle.LAND,savedPassenger.getTransport().getTypeVehicle(), "El pasagero en estudio esta asociado al type transport LAND");
    }

}
