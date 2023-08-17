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

public class AddressDtoTest {
    @ParameterizedTest(name = "{index} street:{0}-city:{1}-brand:{2}-expected:{3}")
    @CsvSource(value = {",,false", ",Cienfuegos,false", "Prado,,false", "Prado,Cienfuegos,true"}, delimiter = ',')
    @DisplayName("Unit AddressDtoTest testValidDto")
    void testValidDto(String street, String city, Boolean expected) {

        AddressDto dto = new AddressDto();
        dto.setCity(city);
        dto.setStreet(street);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<AddressDto>> constraintViolations = validator.validate(dto);

        assertEquals(expected, constraintViolations.isEmpty());

        factory.close();
    }
}

