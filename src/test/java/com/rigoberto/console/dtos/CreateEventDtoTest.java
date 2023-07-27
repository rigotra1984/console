package com.rigoberto.console.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CreateEventDtoTest {
    @ParameterizedTest(name = "{index} priority:{0}-description:{1}-expected:{2}")
    @CsvSource(value = {",,false", "MEDIUM,,false", ",Description,false", "MEDIUM,Description,false", "MEDIUM,Descrip,true"}, delimiter = ',')
    @DisplayName("Unit CreateEventDto testValidDto")
    void testValidDto(String priority, String description, Boolean expected) {
        CreateEventDto dto = new CreateEventDto();
        dto.setPriority(priority);
        dto.setDescription(description);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<CreateEventDto>> constraintViolations = validator.validate(dto);

        assertEquals(expected, constraintViolations.isEmpty());

        factory.close();
    }
}
