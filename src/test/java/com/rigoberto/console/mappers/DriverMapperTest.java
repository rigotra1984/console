package com.rigoberto.console.mappers;

import com.rigoberto.console.dtos.CreateDriverDto;
import com.rigoberto.console.dtos.DriverDto;
import com.rigoberto.console.entities.Driver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriverMapperTest {
    @ParameterizedTest(name = "{index} passport:{0}-name:{1}-expected:{2}")
    @CsvSource(value = {"84020315200,Rigo,True", "84021014200,yaser,true"}, delimiter = ',')
    @DisplayName("Unit Driver testValidDtoToEntity")
    void testValidDtoToEntity(String name, String passport, Boolean expected) {
        CreateDriverDto dto = new CreateDriverDto();
        dto.setName(name);
        dto.setPassport(passport);

        DriverMapper mapper = new DriverMapper(20);
        Driver entity = mapper.convertToEntity(dto);

        assertEquals(expected, entity.getPassport().equals(passport) && entity.getName().equals(name));

    }

    @ParameterizedTest(name = "{index} passport:{1}-name:{2}-expected:{3}")
    @CsvSource(value = {"1,84020315200,Rigo,True", "2,84021014200,yaser,true"}, delimiter = ',')
    @DisplayName("Unit Driver testValidEntityToDto")
    void testValidEntityToDto(Integer id, String name, String passport, Boolean expected) {
        Driver entity = new Driver();
        entity.setId(id);
        entity.setName(name);
        entity.setPassport(passport);

        DriverMapper mapper = new DriverMapper(20);
        DriverDto dto = mapper.convertToDto(entity);

        assertEquals(expected, dto.getId().equals(id) && dto.getName().equals(name) && dto.getPassport().equals(passport));
    }
}
