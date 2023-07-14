package com.rigoberto.console6.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateEventDto {
	@NotEmpty(message = "Priority is required")
	@Pattern(regexp = "/MINIMUN|MEDIUM|MINIMUN/", message = "Priority is invalid")
	private String priority;

	@NotEmpty(message = "Description is required")
	@Size(min = 2, max = 10, message = "Priority is invalid")
	private String description;
}
