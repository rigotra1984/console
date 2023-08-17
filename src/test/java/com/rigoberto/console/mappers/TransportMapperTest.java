package com.rigoberto.console.mappers;

import com.rigoberto.console.dtos.CreateTransportDto;
import com.rigoberto.console.dtos.TransportDto;
import com.rigoberto.console.entities.Destination;
import com.rigoberto.console.entities.Transport;
import com.rigoberto.console.entities.TypeVehicle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportMapperTest {
    @ParameterizedTest(name = "{index} typeVehicle:{0}-destination:{1}-brand:{2}-expected:{3}")
    @CsvSource(value = {"LAND,PASSAGE,toyota,true", "MARITIME ,PASSAGE,toyota,true", "LAND,BURDEN,hiunday,true"}, delimiter = ',')
    @DisplayName("Unit testValidDtoToEntity")
    void testValidDtoToEntity(String typeVehicle, String destination, String brand, Boolean expected) {
        CreateTransportDto dto = new CreateTransportDto();
        dto.setTypeVehicle(typeVehicle);
        dto.setDestination(destination);
        dto.setBrand(brand);

        TransportMapper mapper = new TransportMapper(20);
        Transport entity = mapper.convertToEntity(dto);

        assertEquals(expected, entity.getTypeVehicle().equals(TypeVehicle.valueOf(typeVehicle)) &&
                entity.getDestination().equals(Destination.valueOf(destination)) &&
                entity.getBrand().equals(brand));

    }

    @ParameterizedTest(name = "{index} typeVehicle:{1}-destination:{2}-brand:{3}-expected:{4}")
    @CsvSource(value = {"1,MARITIME,BURDEN,Sea,true", "2,LAND,PASSAGE,toyota,true", "3,LAND,BURDEN,hiunday,true"}, delimiter = ',')
    @DisplayName("Unit testValidEntityToDto")
    void testValidEntityToDto(Integer id, String typeVehicle, String destination, String brand, Boolean expected) {
        Transport entity = new Transport();
        entity.setId(id);
        entity.setRegistrationDate(new Date(System.currentTimeMillis()));
        entity.setTypeVehicle(TypeVehicle.valueOf(typeVehicle));
        entity.setDestination(Destination.valueOf(destination));
        entity.setBrand(brand);

        TransportMapper mapper = new TransportMapper(20);
        TransportDto dto = mapper.convertToDto(entity);

        assertEquals(expected, dto.getId().equals(id) &&
                dto.getTypeVehicle().equals(typeVehicle) &&
                dto.getDestination().equals(destination) &&
                dto.getBrand().equals(brand) && dto.getRegistrationDate() != null);
    }
}

