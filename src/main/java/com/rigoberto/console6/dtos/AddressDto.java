package com.rigoberto.console6.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressDto {
	@NotEmpty(message = "Street is required")
	private String street;

	@NotEmpty(message = "City is required")
	private String city;
}
