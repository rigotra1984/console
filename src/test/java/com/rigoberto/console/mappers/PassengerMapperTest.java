package com.rigoberto.console.mappers;

import com.rigoberto.console.dtos.CreateTransportDto;
import com.rigoberto.console.entities.Destination;
import com.rigoberto.console.entities.Transport;
import com.rigoberto.console.entities.TypeVehicle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PassengerMapperTest {
//    @ParameterizedTest(name = "{index} name:{0}-street:{1}-city:{2}-trasport:{3}-expected:{4}")
//    @CsvSource(value = {"name, Prado, Cienfuegos,1,true", "MARITIME ,PASSAGE,toyota,1,true", "LAND,BURDEN,hiunday,1,true"}, delimiter = ',')
//    @DisplayName("Unit testValidDtoToEntity")
//    void testValidDtoToEntity(String name, String street, String city, Transport transport_id Boolean expected) {
//        CreateTransportDto dto = new CreateTransportDto();
//        dto.setTypeVehicle(typeVehicle);
//        dto.setDestination(destination);
//        dto.setBrand(brand);
//
//        TransportMapper mapper = new TransportMapper(20);
//        Transport entity = mapper.convertToEntity(dto);
//
//        assertEquals(expected, entity.getTypeVehicle().equals(TypeVehicle.valueOf(typeVehicle)) &&
//                entity.getDestination().equals(Destination.valueOf(destination)) &&
//                entity.getBrand().equals(brand));
//
//    }
}
