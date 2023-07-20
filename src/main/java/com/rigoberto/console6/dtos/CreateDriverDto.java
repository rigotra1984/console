package com.rigoberto.console6.dtos;

import com.rigoberto.console6.entities.Transport;
import jakarta.validation.constraints.NotEmpty;
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
	private String passport;

	private Set<Integer> transports;
}
