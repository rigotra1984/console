package com.rigoberto.console.mappers;

import com.rigoberto.console.dtos.*;
import com.rigoberto.console.entities.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TransportMapper {

    private final Integer pageSize;
    private final ModelMapper modelMapper;



    public TransportMapper(@Value("${console6p1.page-size}") Integer pageSize) {
        this.pageSize = pageSize;
        modelMapper = new ModelMapper();

        //ajustar mapeos
        TypeMap<Transport, TransportDto> propertyMapper1 = modelMapper.createTypeMap(Transport.class, TransportDto.class);

        Converter<Destination, String> destinationToString = c -> c.getSource().toString();
        propertyMapper1.addMappings(
                mapper -> mapper.using(destinationToString).map(Transport::getDestination, TransportDto::setDestination)
        );
        Converter<Set<Passenger>, List<BasePassengerDto>> passengersToDto = c -> c.getSource() != null? c.getSource().stream().map(x -> new BasePassengerDto(x.getId(), x.getName())).toList(): new ArrayList<>();
        propertyMapper1.addMappings(
                mapper -> mapper.using(passengersToDto).map(Transport::getPassengers, TransportDto::setPassengers)
        );
        Converter<Set<Driver>, List<BaseDriverDto>> driversToDto = c -> c.getSource() != null? c.getSource().stream().map(x -> new BaseDriverDto(x.getId(), x.getName(), x.getPassport())).toList(): new ArrayList<>();
        propertyMapper1.addMappings(
                mapper -> mapper.using(driversToDto).map(Transport::getDrivers, TransportDto::setDrivers)
        );

        Converter<TypeVehicle, String> typeVehicleToString = c -> c.getSource().toString();
        propertyMapper1.addMappings(
                mapper -> mapper.using(typeVehicleToString).map(Transport::getTypeVehicle, TransportDto::setTypeVehicle)
        );

        TypeMap<CreateTransportDto, Transport> propertyMapper2 = modelMapper.createTypeMap(CreateTransportDto.class, Transport.class);
        propertyMapper2.addMappings(mapper -> mapper.skip(Transport::setId));
        propertyMapper2.addMappings(
                mapper -> mapper.map(src -> new Date(System.currentTimeMillis()), Transport::setRegistrationDate)
        );
        Converter<String, Destination> stringToDestination = c -> Destination.fromString(c.getSource());
        propertyMapper2.addMappings(
                mapper -> mapper.using(stringToDestination).map(CreateTransportDto::getDestination, Transport::setDestination)
        );
        Converter<String, TypeVehicle> stringToTypeVehicle = c -> TypeVehicle.fromString(c.getSource());
        propertyMapper2.addMappings(
                mapper -> mapper.using(stringToTypeVehicle).map(CreateTransportDto::getTypeVehicle, Transport::setTypeVehicle)
        );
    }

    public Transport convertToEntity(CreateTransportDto dto) {
        return modelMapper.map(dto, Transport.class);
    }

    public TransportDto convertToDto(Transport entity) {
        return modelMapper.map(entity, TransportDto.class);
    }

    public PageDto<TransportDto> convertToDto(Page<Transport> page) {
        PageDto<TransportDto> pageDto = new PageDto<>();
        pageDto.setPage(page.getNumber() + 1);
        pageDto.setTotalElements(page.getTotalElements());
        pageDto.setTotalPages(page.getTotalPages());
        pageDto.setItems(page.stream().map(this::convertToDto).collect(Collectors.toList()));
        pageDto.setPageSize(pageSize);

        return pageDto;
    }
}