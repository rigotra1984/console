package com.rigoberto.console.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatePassengerDtoTest {
    @ParameterizedTest(name = "{index} name:{0}-trasportId:{1}-Street:{2}-City{3}-expected:{4}")
    @CsvSource(value = {",,,,false",",1,Preado,Cienfuegos,false","yaser,2,,,true","yaser,1,Prado,Cienfuegos,true",}, delimiter = ',')
    @DisplayName("Unit CreatePassengerDto testValidDto")
    void testValidDto(String name, Integer transportId, String street, String city, Boolean expected) {
        CreatePassengerDto dto = new CreatePassengerDto();
        dto.setName(name);
        dto.setTransportId(transportId);

        AddressDto addressDto = new AddressDto();
        addressDto.setStreet(street);
        addressDto.setCity(city);
        dto.setAddress(addressDto);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<CreatePassengerDto>> constraintViolations = validator.validate(dto);

        assertEquals(expected, constraintViolations.isEmpty());

        factory.close();
    }
}

//    @NotEmpty(message = "Priority is required")
//    private String name;
//    @Positive(message = "TransportId is invalid")
//    private Integer transportId;

//    private AddressDto address;
