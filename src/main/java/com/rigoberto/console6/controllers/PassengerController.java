package com.rigoberto.console6.controllers;

import com.rigoberto.console6.dtos.*;
import com.rigoberto.console6.entities.Event;
import com.rigoberto.console6.entities.Passenger;
import com.rigoberto.console6.entities.Priority;
import com.rigoberto.console6.entities.Transport;
import com.rigoberto.console6.exceptions.NotFoundException;
import com.rigoberto.console6.mappers.PassengerMapper;
import com.rigoberto.console6.services.PassengerService;
import com.rigoberto.console6.services.TransportService;
import com.rigoberto.console6.utils.Streams;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController {
    private final PassengerService service;
    private final TransportService transportService;
    private final PassengerMapper mapper;

    public PassengerController(PassengerService service, TransportService transportService, PassengerMapper mapper) {
        this.service = service;
        this.transportService = transportService;
        this.mapper = mapper;
    }

    @Operation(summary = "Get all passengers")
    @GetMapping
    public Collection<PassengerDto> getAll() {
        return Streams.streamOf(service.findAll()).map(mapper::convertToDto).collect(Collectors.toList());
    }

    @Operation(summary = "Get a passengers by its page")
    @GetMapping("/page/{page}")
    public PageDto<PassengerDto> getAllByPage(@PathVariable Integer page) {
        return mapper.convertToDto(service.findAll(page));
    }

    @Operation(summary = "Get a passenger by its id")
    @GetMapping("/{id}")
    public PassengerDto getById(@PathVariable Integer id) {
        Optional<Passenger> inbox = service.findById(id);

        if(inbox.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        return mapper.convertToDto(inbox.get());
    }

    @Operation(summary = "Create new passenger")
    @PostMapping
    public PassengerDto create(@Valid @RequestBody CreatePassengerDto dto) {
        Optional<Transport> transport = transportService.findById(dto.getTransportId());

        if(transport.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        Passenger entity = mapper.convertToEntity(dto);
        entity.setTransport(transport.get());

        Passenger result = service.save(entity);

        return mapper.convertToDto(result);
    }

    @Operation(summary = "Update a passenger by its id")
    @PutMapping("/{id}")
    public PassengerDto update(@PathVariable Integer id, @Valid @RequestBody CreatePassengerDto dto) {
        Optional<Passenger> entity = service.findById(id);

        if(entity.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        Optional<Transport> transport = transportService.findById(dto.getTransportId());

        if(transport.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        Passenger event = entity.get();
        event.setName(dto.getName());
        event.setTransport(transport.get());
        Passenger result = service.save(event);

        return mapper.convertToDto(result);
    }

    @Operation(summary = "Delete a passenger by its id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        Optional<Passenger> inbox = service.findById(id);

        if(inbox.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        service.deleteById(id);
    }

}
