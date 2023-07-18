package com.rigoberto.console6.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {
    private final Integer pageSize;
    private final ModelMapper modelMapper;

    public PassengerMapper(@Value("${console6p1.page-size}") Integer pageSize) {
        this.pageSize = pageSize;
        modelMapper = new ModelMapper();
    }
}
