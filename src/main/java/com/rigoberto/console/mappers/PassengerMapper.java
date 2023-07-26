package com.rigoberto.console.mappers;

import com.rigoberto.console.dtos.*;
import com.rigoberto.console.entities.Passenger;
import com.rigoberto.console.entities.Transport;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PassengerMapper {
    private final Integer pageSize;
    private final ModelMapper modelMapper;

    public PassengerMapper(@Value("${console6p1.page-size}") Integer pageSize) {
        this.pageSize = pageSize;
        modelMapper = new ModelMapper();

        TypeMap<Passenger, PassengerDto> propertyMapper1 = modelMapper.createTypeMap(Passenger.class, PassengerDto.class);
        Converter<Transport, Integer> transportToInteger = c -> c.getSource().getId();
        propertyMapper1.addMappings(
                mapper -> mapper.using(transportToInteger).map(Passenger::getTransport, PassengerDto::setTransportId)
        );

        TypeMap<CreatePassengerDto, Passenger> propertyMapper2 = modelMapper.createTypeMap(CreatePassengerDto.class, Passenger.class);
        propertyMapper2.addMappings(mapper -> mapper.skip(Passenger::setId));
    }

    public Passenger convertToEntity(CreatePassengerDto dto) {
        return modelMapper.map(dto, Passenger.class);
    }

    public PassengerDto convertToDto(Passenger entity) {
        return modelMapper.map(entity, PassengerDto.class);
    }

    public PageDto<PassengerDto> convertToDto(Page<Passenger> page) {
        PageDto<PassengerDto> pageDto = new PageDto<>();
        pageDto.setPage(page.getNumber() + 1);
        pageDto.setTotalElements(page.getTotalElements());
        pageDto.setTotalPages(page.getTotalPages());
        pageDto.setItems(page.stream().map(this::convertToDto).collect(Collectors.toList()));
        pageDto.setPageSize(pageSize);

        return pageDto;
    }
}
