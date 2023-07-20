package com.rigoberto.console6.mappers;

import com.rigoberto.console6.dtos.*;
import com.rigoberto.console6.entities.Driver;
import com.rigoberto.console6.entities.Transport;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DriverMapper {
    private final ModelMapper modelMapper;
    private final Integer pageSize;

    public DriverMapper(@Value("${console6p1.page-size}") Integer pageSize) {
        this.pageSize = pageSize;
        modelMapper = new ModelMapper();

        //ajustar mapeos
        TypeMap<Driver, DriverDto> propertyMapper1 = modelMapper.createTypeMap(Driver.class, DriverDto.class);
        Converter<Set<Transport>, List<BaseTransportDto>> transportToInteger = c -> c.getSource() != null? c.getSource().stream().map(x -> new BaseTransportDto(x.getId(), x.getRegistrationDate(), x.getDestination().toString(), x.getTypeVehicle().toString(), x.getBrand())).toList(): new ArrayList<>();
        propertyMapper1.addMappings(
                mapper -> mapper.using(transportToInteger).map(Driver::getTransports, DriverDto::setTransports)
        );

        TypeMap<CreateDriverDto, Driver> propertyMapper2 = modelMapper.createTypeMap(CreateDriverDto.class, Driver.class);
        propertyMapper2.addMappings(mapper -> mapper.skip(Driver::setId));
    }

    public Driver convertToEntity(CreateDriverDto dto) {
        return modelMapper.map(dto, Driver.class);
    }

    public DriverDto convertToDto(Driver entity) {
        return modelMapper.map(entity, DriverDto.class);
    }

    public PageDto<DriverDto> convertToDto(Page<Driver> page) {
        PageDto<DriverDto> pageDto = new PageDto<>();
        pageDto.setPage(page.getNumber() + 1);
        pageDto.setTotalElements(page.getTotalElements());
        pageDto.setTotalPages(page.getTotalPages());
        pageDto.setItems(page.stream().map(this::convertToDto).collect(Collectors.toList()));
        pageDto.setPageSize(pageSize);

        return pageDto;
    }
}
