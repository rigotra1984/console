package com.rigoberto.console.mappers;

import com.rigoberto.console.dtos.CreateEventDto;
import com.rigoberto.console.dtos.EventDto;
import com.rigoberto.console.dtos.PageDto;
import com.rigoberto.console.entities.Event;
import com.rigoberto.console.entities.Priority;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class EventMapper {
    private final ModelMapper modelMapper;
    private final Integer pageSize;

    public EventMapper(@Value("${console6p1.page-size}") Integer pageSize) {
        this.pageSize = pageSize;
        modelMapper = new ModelMapper();

        //ajustar mapeos
        TypeMap<Event, EventDto> propertyMapper1 = modelMapper.createTypeMap(Event.class, EventDto.class);
        Converter<Priority, String> priorityToString = c -> c.getSource().toString();
        propertyMapper1.addMappings(
                mapper -> mapper.using(priorityToString).map(Event::getPriority, EventDto::setPriority)
        );

        TypeMap<CreateEventDto, Event> propertyMapper2 = modelMapper.createTypeMap(CreateEventDto.class, Event.class);
        propertyMapper2.addMappings(mapper -> mapper.skip(Event::setId));
        propertyMapper2.addMappings(
                mapper -> mapper.map(src -> new Date(System.currentTimeMillis()), Event::setDate)
        );
        Converter<String, Priority> stringToPriority = c -> Priority.fromString(c.getSource());
        propertyMapper2.addMappings(
                mapper -> mapper.using(stringToPriority).map(CreateEventDto::getPriority, Event::setPriority)
        );
    }

    public Event convertToEntity(CreateEventDto dto) {
        return modelMapper.map(dto, Event.class);
    }

    public EventDto convertToDto(Event entity) {
        return modelMapper.map(entity, EventDto.class);
    }

    public PageDto<EventDto> convertToDto(Page<Event> page) {
        PageDto<EventDto> pageDto = new PageDto<>();
        pageDto.setPage(page.getNumber() + 1);
        pageDto.setTotalElements(page.getTotalElements());
        pageDto.setTotalPages(page.getTotalPages());
        pageDto.setItems(page.stream().map(this::convertToDto).collect(Collectors.toList()));
        pageDto.setPageSize(pageSize);

        return pageDto;
    }
}
