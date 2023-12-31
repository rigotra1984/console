package com.rigoberto.console6.controllers;

import com.rigoberto.console6.dtos.CreateTransportDto;
import com.rigoberto.console6.dtos.PageDto;
import com.rigoberto.console6.dtos.TransportDto;
import com.rigoberto.console6.entities.Destination;
import com.rigoberto.console6.entities.Transport;
import com.rigoberto.console6.entities.TypeVehicle;
import com.rigoberto.console6.exceptions.NotFoundException;
import com.rigoberto.console6.mappers.TransportMapper;
import com.rigoberto.console6.services.TransportService;
import com.rigoberto.console6.utils.Streams;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transport")
public class TransportController {
    private final TransportService service;
    private final TransportMapper mapper;

    public TransportController(TransportService service, TransportMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Collection<TransportDto> getAll() {
        return Streams.streamOf(service.findAll()).map(mapper::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/page/{page}")
    public PageDto<TransportDto> getAllByPage(@PathVariable Integer page) {
        return mapper.convertToDto(service.findAll(page));
    }

    @GetMapping("/{id}")
    public TransportDto getById(@PathVariable Integer id) {
        Optional<Transport> inbox = service.findById(id);

        if (inbox.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        return mapper.convertToDto(inbox.get());
    }

    @PostMapping
    public TransportDto create(@Valid @RequestBody CreateTransportDto dto) {
        Transport result = service.save(mapper.convertToEntity(dto));

        return mapper.convertToDto(result);
    }

    @PutMapping("/{id}")
    public TransportDto update(@PathVariable Integer id, @Valid @RequestBody CreateTransportDto dto) {
        Optional<Transport> entity = service.findById(id);

        if (entity.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        Transport transport = entity.get();
        transport.setDestination(Destination.fromString(dto.getDestination()));
        transport.setTypeVehicle(TypeVehicle.fromString(dto.getTypeVehicle()));
        transport.setBrand(dto.getBrand());
        Transport result = service.save(transport);

        return mapper.convertToDto(result);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        Optional<Transport> inbox = service.findById(id);

        if (inbox.isEmpty()) {
            throw new NotFoundException("Element not found");
        }

        service.deleteById(id);
    }
}
