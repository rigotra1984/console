package com.rigoberto.console.controllers;

import com.rigoberto.console.dtos.CreateTransportDto;
import com.rigoberto.console.dtos.PageDto;
import com.rigoberto.console.dtos.TransportDto;
import com.rigoberto.console.entities.Destination;
import com.rigoberto.console.entities.Driver;
import com.rigoberto.console.entities.Transport;
import com.rigoberto.console.entities.TypeVehicle;
import com.rigoberto.console.exceptions.NotFoundException;
import com.rigoberto.console.mappers.TransportMapper;
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
@RequestMapping("/api/transport")
public class TransportController {
    private Logger logger = LogManager.getLogger(TransportController.class);
    private final TransportService service;
    private final DriverService driverService;
    private final TransportMapper mapper;

    public TransportController(TransportService service, DriverService driverService, TransportMapper mapper) {
        this.service = service;
        this.driverService = driverService;
        this.mapper = mapper;
    }

    @Operation(summary = "Get all transports")
    @GetMapping
    public Collection<TransportDto> getAll() {
        try {
            logger.info("Deberia funcionar OK");
            return Streams.streamOf(service.findAll()).map(mapper::convertToDto).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Ocurrio un error al intentar obtener todos los Transportes", e);
            throw e;
        }
    }

    @Operation(summary = "Get a transports by its page")
    @GetMapping("/page/{page}")
    public PageDto<TransportDto> getAllByPage(@PathVariable Integer page) {
        try{
            return mapper.convertToDto(service.findAll(page));
        } catch (Exception e){
            logger.error("Ocurrio un error al intentar obtener los trasnportes por paginas", e);
            throw e;
        }

    }

    @Operation(summary = "Get a transport by its id")
    @GetMapping("/{id}")
    public TransportDto getById(@PathVariable Integer id) {
        Optional<Transport> inbox = service.findById(id);

        if (inbox.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        return mapper.convertToDto(inbox.get());
    }

    @Operation(summary = "Create new transport")
    @PostMapping
    public TransportDto create(@Valid @RequestBody CreateTransportDto dto) {
        Transport entity = mapper.convertToEntity(dto);

        Collection<Driver> drivers = driverService.findAllByIdIn(dto.getDrivers());
        entity.setDrivers(Set.copyOf(drivers));

        Transport result = service.save(entity);

        return mapper.convertToDto(result);
    }

    @Operation(summary = "Update a transport by its id")
    @PutMapping("/{id}")
    public TransportDto update(@PathVariable Integer id, @Valid @RequestBody CreateTransportDto dto) {
        Optional<Transport> entity = service.findById(id);

        if (entity.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        Collection<Driver> drivers = driverService.findAllByIdIn(dto.getDrivers());

        Transport transport = entity.get();
        transport.setDestination(Destination.fromString(dto.getDestination()));
        transport.setTypeVehicle(TypeVehicle.fromString(dto.getTypeVehicle()));
        transport.setBrand(dto.getBrand());
        transport.setDrivers(Set.copyOf(drivers));
        Transport result = service.save(transport);

        return mapper.convertToDto(result);
    }

    @Operation(summary = "Delete a transport by its id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        Optional<Transport> inbox = service.findById(id);

        if (inbox.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        service.deleteById(id);
    }
}
