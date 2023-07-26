package com.rigoberto.console.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreatePassengerDto {
	@NotEmpty(message = "Priority is required")
	private String name;

	@Positive(message = "TransportId is invalid")
	private Integer transportId;

	private AddressDto address;
}
