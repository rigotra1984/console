package com.rigoberto.console.dtos;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;

@Data
public class ApiErrorDto implements Serializable {
    private String errorId;
    private Date timestamp;
    private HttpStatus httpStatus;
    private String message;
    private String path;
}
