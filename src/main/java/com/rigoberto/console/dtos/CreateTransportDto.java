package com.rigoberto.console.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class CreateTransportDto {
	@NotEmpty(message = "Type of Vehicle is required")
	@Pattern(regexp = "MARITIME|AERIAL|LAND", message = "Type of Vehicle is invalid")
	private String typeVehicle;

	@NotEmpty(message = "Destination is required")
	@Pattern(regexp = "BURDEN|PASSAGE|WALK", message = "Destination is invalid")
	private String destination;

	@NotEmpty(message = "Brand is required")
	@Size(min = 2, max = 15, message = "Brand is invalid")
	private String brand;

	private Set<Integer> drivers;
}
