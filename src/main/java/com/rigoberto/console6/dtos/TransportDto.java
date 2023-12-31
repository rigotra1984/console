package com.rigoberto.console6.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class TransportDto {
	private Integer id;
	private Date registrationDate;
	private String destination;
	private String typeVehicle;
	private String brand;
}
