package com.rigoberto.console.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreatePassengerDto {
	@NotEmpty(message = "Priority is required")
	@NotNull(message = "El nombre no puede estar vacio")
	private String name;

	@Positive(message = "TransportId is invalid")
	private Integer transportId;

	private AddressDto address;
}
