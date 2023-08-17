package com.rigoberto.console.mappers;

import com.rigoberto.console.dtos.*;
import com.rigoberto.console.entities.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PassengerMapperTest {
    @ParameterizedTest(name = "{index} name:{0}-street:{1}-city:{2}-trasport:{3}-expected:{4}")
    @CsvSource(value = {"Yaser, Prado, Cienfuegos,1,true", " yass,Pta Gorda,Cienfuegos,1,true", "LAND,BURDEN,hiunday,1,true"}, delimiter = ',')
    @DisplayName("Unit testValidDtoToEntity")
    void testValidDtoToEntity(String name, String street, String city, Integer transport_id, Boolean expected) {
        CreatePassengerDto dto = new CreatePassengerDto();
        dto.setName(name);
        dto.setTransportId(transport_id);

        AddressDto addressDto = new AddressDto();
        addressDto.setStreet(street);
        addressDto.setCity(city);
        dto.setAddress(addressDto);

        PassengerMapper mapper = new PassengerMapper(20);
        Passenger entity = mapper.convertToEntity(dto);

        assertEquals(expected, entity.getName().equals(name) &&
                entity.getAddress().getStreet().equals(street) &&
                entity.getAddress().getCity().equals(city) &&
                entity.getTransport().getId().equals(transport_id));

    }

    @ParameterizedTest(name = "{index} priority:{1}-description:{2}-expected:{3}")
    @CsvSource(value = {"1,Yaser,Prado, Cienfuegos, 1,true", "2,Rigo, Junco Sur, Cienfuegos,1,true"}, delimiter = ',')
    @DisplayName("Unit Event testValidEntityToDto")
    void testValidEntityToDto(Integer id, String name, String street, String city, Integer transport_id, Boolean expected) {
        Passenger entity = new Passenger();

        Address adr = new Address();
        adr.setId(id);
        adr.setCity(city);
        adr.setStreet(street);

        entity.setId(id);
        entity.setName(name);

        entity.setAddress(adr);

        Transport transport1 = new Transport(transport_id, new Date(System.currentTimeMillis()), TypeVehicle.LAND, Destination.WALK, "Mercedez", new HashSet<>(), new HashSet<>());
        transport1.getPassengers().add(entity);

        entity.setTransport(transport1);

        PassengerMapper mapper = new PassengerMapper(20);
        PassengerDto dto = mapper.convertToDto(entity);

        assertEquals(expected, dto.getId().equals(id) && dto.getName().equals(name) && dto.getAddress().getStreet().equals(street) && dto.getAddress().getCity().equals(city) && dto.getTransportId().equals(transport_id));
    }
}
