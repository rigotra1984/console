package com.rigoberto.console.controllers;

import com.rigoberto.console.dtos.*;
import com.rigoberto.console.entities.Driver;
import com.rigoberto.console.entities.Transport;
import com.rigoberto.console.exceptions.NotFoundException;
import com.rigoberto.console.mappers.DriverMapper;
import com.rigoberto.console.services.DriverService;
import com.rigoberto.console.services.TransportService;
import com.rigoberto.console.utils.Streams;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/driver")
public class DriverController {
    private Logger logger = LogManager.getLogger(DriverController.class);
    private final DriverService service;
    private final TransportService transportService;
    private final DriverMapper mapper;

    public DriverController(DriverService service, TransportService transportService, DriverMapper mapper) {
        this.service = service;
        this.transportService = transportService;
        this.mapper = mapper;
    }

    @Operation(summary = "Get all drivers")
    @GetMapping
    public Collection<DriverDto> getAll() {
        try {
            return Streams.streamOf(service.findAll()).map(mapper::convertToDto).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Ocurrio un error al intentar obtener todos los Passenger", e);
            throw e;
        }
    }

    @Operation(summary = "Get a drivers by its page")
    @GetMapping("/page/{page}")
    public PageDto<DriverDto> getAllByPage(@PathVariable Integer page) {
        return mapper.convertToDto(service.findAll(page));
    }

    @Operation(summary = "Get a driver by its id")
    @GetMapping("/{id}")
    public DriverDto getById(@PathVariable Integer id) {
        Optional<Driver> inbox = service.findById(id);

        if (inbox.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        return mapper.convertToDto(inbox.get());
    }

    @Operation(summary = "Create new driver")
    @PostMapping
    public DriverDto create(@Valid @RequestBody CreateDriverDto dto) {
        Driver entity = mapper.convertToEntity(dto);

        Collection<Transport> transports = transportService.findAllByIdIn(dto.getTransports());
        entity.setTransports(Set.copyOf(transports));

        Driver result = service.save(entity);

        return mapper.convertToDto(result);
    }

    @Operation(summary = "Update a driver by its id")
    @PutMapping("/{id}")
    public DriverDto update(@PathVariable Integer id, @Valid @RequestBody CreateDriverDto dto) {
        Optional<Driver> entity = service.findById(id);

        if (entity.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        Collection<Transport> transports = transportService.findAllByIdIn(dto.getTransports());

        Driver driver = entity.get();
        driver.setName(dto.getName());
        driver.setPassport(dto.getPassport());
        driver.setTransports(Set.copyOf(transports));
        Driver result = service.save(driver);

        return mapper.convertToDto(result);
    }

    @Operation(summary = "Delete a driver by its id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        Optional<Driver> inbox = service.findById(id);

        if (inbox.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        service.deleteById(id);
    }

}
