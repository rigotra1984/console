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

public class CreateDriverDtoTest {
    @ParameterizedTest(name = "{index} name:{0}-passport:{1}-expected:{2}")
    @CsvSource(value = {",,false", "yaser,,false", ",84021014200,false", "yaser,840210,false", "yaser,84021014200,true"}, delimiter = ',')
    @DisplayName("Unit CreateDriverDto testValidDto")
    void testValidDto(String name, String passport, Boolean expected) {

        CreateDriverDto dto = new CreateDriverDto();
        dto.setName(name);
        dto.setPassport(passport);


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<CreateDriverDto>> constraintViolations = validator.validate(dto);

        assertEquals(expected, constraintViolations.isEmpty());

        factory.close();
    }
}
