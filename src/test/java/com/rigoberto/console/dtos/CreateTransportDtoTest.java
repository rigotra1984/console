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

public class CreateTransportDtoTest {
    @ParameterizedTest(name = "{index} typeVehicle:{0}-destination:{1}-brand:{2}-expected:{3}")
    @CsvSource(value = {",,,false", ",,toyota,false", "MARITIME,,toyota,false", ",PASSAGE,toyota,false",
            "PASSAGE,MARITIME,toyota,false", "LAND,BURDEN,hiunday,true", "LAND,WALK,yaris,true", "LAND,WALK,Ferrari,True"}, delimiter = ',')
    @DisplayName("Unit CreateTransportDto testValidDto")
    void testValidDto(String typeVehicle, String destination, String brand, Boolean expected) {

        CreateTransportDto dto = new CreateTransportDto();
        dto.setTypeVehicle(typeVehicle);
        dto.setDestination(destination);
        dto.setBrand(brand);
//        if (drivers != null) {
//            Set<Integer> driverSet = Stream.of(drivers.split(";"))
//                    .map(Integer::valueOf)
//                    .collect(Collectors.toSet());
//            dto.setDrivers(driverSet);
//        }
//        lo mismo para every world
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<CreateTransportDto>> constraintViolations = validator.validate(dto);

        assertEquals(expected, constraintViolations.isEmpty());

        factory.close();
    }
}

//    @ParameterizedTest(name = "{index} typeVehicle:{0}-destination:{1}-brand:{2}-expected:{3}")
//    @CsvSource(value = {}, delimiter = ',')
//    @DisplayName("Unit <Class> testValidDto")
//    void testValidDto(<Parameters>,Boolean expected){
//
//        <Class> dto = new
//        dto.setDestination(destination);
//        dto.set
//        dto.set

//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        final Validator validator = factory.getValidator();
//
//        Set<ConstraintViolation<CreateTransportDto>> constraintViolations = validator.validate(dto);
//
//        assertEquals(expected, constraintViolations.isEmpty());
//
//        factory.close();
//    }