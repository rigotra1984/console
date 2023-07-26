package com.rigoberto.console.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TransportDto extends BaseTransportDto {
	private List<BasePassengerDto> passengers;
	private List<BaseDriverDto> drivers;
}
