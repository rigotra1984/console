package com.rigoberto.console.mappers;

import com.rigoberto.console.dtos.CreateEventDto;
import com.rigoberto.console.dtos.EventDto;
import com.rigoberto.console.entities.Event;
import com.rigoberto.console.entities.Priority;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventMapperTest {
    @ParameterizedTest(name = "{index} priority:{0}-description:{1}-expected:{2}")
    @CsvSource(value = {"MINIMUN,Description1,true", "MEDIUM,Description2,true", "MAXIMUN,Description3,true"}, delimiter = ',')
    @DisplayName("Unit Event testValidDtoToEntity")
    void testValidDtoToEntity(String priority, String description, Boolean expected) {
        CreateEventDto dto = new CreateEventDto();
        dto.setPriority(priority);
        dto.setDescription(description);

        EventMapper mapper = new EventMapper(20);
        Event entity = mapper.convertToEntity(dto);

        assertEquals(expected, entity.getPriority().equals(Priority.valueOf(priority)) && entity.getDescription().equals(description));

    }

    @ParameterizedTest(name = "{index} priority:{1}-description:{2}-expected:{3}")
    @CsvSource(value = {"1,MINIMUN,Description1,true", "2,MEDIUM,Description2,true", "3,MAXIMUN,Description3,true"}, delimiter = ',')
    @DisplayName("Unit Event testValidEntityToDto")
    void testValidEntityToDto(Integer id, String priority, String description, Boolean expected) {
        Event entity = new Event();
        entity.setId(id);
        entity.setDate(new Date(System.currentTimeMillis()));
        entity.setPriority(Priority.valueOf(priority));
        entity.setDescription(description);

        EventMapper mapper = new EventMapper(20);
        EventDto dto = mapper.convertToDto(entity);

        assertEquals(expected, dto.getId().equals(id) && dto.getPriority().equals(priority) && dto.getDescription().equals(description) && dto.getDate() != null);
    }
}
