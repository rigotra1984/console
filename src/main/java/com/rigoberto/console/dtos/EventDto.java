package com.rigoberto.console.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class EventDto {
	private Integer id;
	private Date date;
	private String priority;
	private String description;
}
