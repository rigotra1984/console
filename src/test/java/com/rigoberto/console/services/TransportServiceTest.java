package com.rigoberto.console.services;

import com.rigoberto.console.entities.Destination;
import com.rigoberto.console.entities.Transport;
import com.rigoberto.console.entities.TypeVehicle;
import com.rigoberto.console.repositories.TransportRepository;
import com.rigoberto.console.services.impl.TransportServiceImpl;
import com.rigoberto.console.utils.Streams;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class TransportServiceTest {
    @Mock
    private TransportRepository repository;
    private TransportService service;
    private AutoCloseable closeable;

    @BeforeEach
    void initService() {
        closeable = MockitoAnnotations.openMocks(this);
        service = new TransportServiceImpl(repository, 20);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    private static List<Transport> getTestTransportData() {
        return List.of(
                new Transport(1, new Date(System.currentTimeMillis()), TypeVehicle.LAND, Destination.WALK, "Mercedez", null, null),
                new Transport(2, new Date(System.currentTimeMillis()), TypeVehicle.LAND, Destination.PASSAGE, "Yutong", null, null),
                new Transport(3, new Date(System.currentTimeMillis()), TypeVehicle.LAND, Destination.PASSAGE, "Yutong", null, null),
                new Transport(4, new Date(System.currentTimeMillis()), TypeVehicle.AERIAL, Destination.PASSAGE, "Boing", null, null),
                new Transport(5, new Date(System.currentTimeMillis()), TypeVehicle.LAND, Destination.WALK, "Mercedez", null, null)
        );
    }

    @Test
    @DisplayName("Unit /api/transport findAll")
    void getTestFindAll() {
        Mockito.when(repository.findAll()).thenReturn(getTestTransportData());

        List<Transport> result = Streams.streamOf(service.findAll()).toList();

        assertEquals(5, result.size(), "El findAll del repository debe devolver 5 elementos");
    }

    @Test
    @DisplayName("Unit /api/transport/1 findById")
    void findById() {
        Mockito.when(repository.findById(1)).thenReturn(getTestTransportData().stream().filter(x -> x.getId() == 1).findFirst());

        Optional<Transport> result = service.findById(1);

        assertTrue(result.isPresent(), "El transport con id 1 no existe");

        assertEquals(1, result.get().getId(), "El id del elemento debe ser 1");
        assertEquals("Mercedez", result.get().getBrand(), "La marca del elemento debe ser: \"Mercedez\"");
        assertEquals(Destination.WALK, result.get().getDestination(), "El destino del elemento deber Walk(paseo)");
        assertEquals(TypeVehicle.LAND, result.get().getTypeVehicle(), "El tipo de vehiculo del elemento deber Land( terrestre)");
    }

    @Test
    @DisplayName("Unit /api/transport/1 deleteById")
    void deleteById() {
        int idToDelete = 1;
        Mockito.doNothing().when(repository).deleteById(idToDelete);
//        Mockito.when(repository.deleteById(idToDelete)).;

        service.deleteById(idToDelete);
        verify(repository, times(1)).deleteById(idToDelete);
    }

    @Test
    @DisplayName("Unit /api/transport/testSaveTransport")
    void save() {
        Transport transport = new Transport(6, new Date(System.currentTimeMillis()), TypeVehicle.AERIAL, Destination.PASSAGE, "BlueJays", null, null);

        // Configura el comportamiento simulado para el método save del repositorio
        Mockito.when(repository.save(transport)).thenReturn(transport);

        // Llama al método save del servicio (que internamente utiliza el método save del repositorio)
        Transport savedTransport = service.save(transport);

        // Verifica que el método save del repositorio se haya llamado exactamente una vez con el objeto transport
        verify(repository, times(1)).save(transport);

        // Verifica que los datos del objeto guardado sean los esperados
        assertEquals(6, savedTransport.getId(), "El id del objeto guardado debe ser 6");
        assertEquals("BlueJays", savedTransport.getBrand(), "La marca del objeto guardado debe ser \"BlueJays\"");
        assertEquals(Destination.PASSAGE, savedTransport.getDestination(), "El destino del objeto guardado debe ser Passage(pasajero)");
        assertEquals(TypeVehicle.AERIAL, savedTransport.getTypeVehicle(), "El tipo de vehiculo del objeto guardado debe ser Aerial(aereo)");
    }
}
