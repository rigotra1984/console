package com.rigoberto.console6.dtos;

import com.rigoberto.console6.entities.Transport;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PassengerDto {
    private Integer id;
    private String name;
    private Transport transport;
}
