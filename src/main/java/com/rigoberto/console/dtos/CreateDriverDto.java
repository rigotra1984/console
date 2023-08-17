package com.rigoberto.console.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class CreateDriverDto {
	@NotEmpty(message = "Name is required")
	private String name;

	@NotEmpty(message = "Passport is required")
	@Size(min = 11, max = 11, message = "Invalid Passport")
	private String passport;

	private Set<Integer> transports;
}
