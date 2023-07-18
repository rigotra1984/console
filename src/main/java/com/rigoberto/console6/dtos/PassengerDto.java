package com.rigoberto.console6.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PassengerDto extends PassengerByTransportDto{
    private Integer transportId;
}
