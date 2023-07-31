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
    @ParameterizedTest(name = "{index} name:{0}-trasportId:{1}-expected:{2}")
    @CsvSource(value = {",,false",",1,false","yaser,2,false","yaser,1,true",}, delimiter = ',')
    @DisplayName("Unit CreatePassengerDto testValidDto")
    void testValidDto(String name, Integer transportId, Boolean expected) {
        CreatePassengerDto dto = new CreatePassengerDto();
        dto.setName(name);
        dto.setTransportId(transportId);

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
