package com.rigoberto.console6.controllers;

import com.rigoberto.console6.dtos.CreateEventDto;
import com.rigoberto.console6.dtos.EventDto;
import com.rigoberto.console6.dtos.PageDto;
import com.rigoberto.console6.entities.Event;
import com.rigoberto.console6.entities.Priority;
import com.rigoberto.console6.exceptions.NotFoundException;
import com.rigoberto.console6.mappers.EventMapper;
import com.rigoberto.console6.services.EventService;
import com.rigoberto.console6.utils.Streams;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/event")
public class EventController {
    private final EventService service;
    private final EventMapper mapper;

    public EventController(EventService service, EventMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Get all events")
    @GetMapping
    public Collection<EventDto> getAll() {
        return Streams.streamOf(service.findAll()).map(mapper::convertToDto).collect(Collectors.toList());
    }

    @Operation(summary = "Get a events by its page")
    @GetMapping("/page/{page}")
    public PageDto<EventDto> getAllByPage(@PathVariable Integer page) {
        return mapper.convertToDto(service.findAll(page));
    }

    @Operation(summary = "Get a event by its id")
    @GetMapping("/{id}")
    public EventDto getById(@PathVariable Integer id) {
        Optional<Event> inbox = service.findById(id);

        if(inbox.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        return mapper.convertToDto(inbox.get());
    }

    @Operation(summary = "Create new event")
    @PostMapping
    public EventDto create(@Valid @RequestBody CreateEventDto dto) {
        Event result = service.save(mapper.convertToEntity(dto));

        return mapper.convertToDto(result);
    }

    @Operation(summary = "Update a event by its id")
    @PutMapping("/{id}")
    public EventDto update(@PathVariable Integer id, @Valid @RequestBody CreateEventDto dto) {
        Optional<Event> entity = service.findById(id);

        if(entity.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        Event event = entity.get();
        event.setPriority(Priority.fromString(dto.getPriority()));
        event.setDescription(dto.getDescription());
        Event result = service.save(event);

        return mapper.convertToDto(result);
    }

    @Operation(summary = "Delete a event by its id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        Optional<Event> inbox = service.findById(id);

        if(inbox.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        service.deleteById(id);
    }
}
