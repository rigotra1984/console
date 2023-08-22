package com.rigoberto.console.dtos;

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
	@NotEmpty(message = "{priority_required}")
	@Pattern(regexp = "/MAXIMUN|MEDIUM|MINIMUN/", message="{priority_invalid}")
	private String priority;

	@NotEmpty(message = "{description_required}")
	private String description;
}
