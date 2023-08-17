package com.rigoberto.console.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UserDto {
    private String sid;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private Boolean emailVerified;
    private List<String> roles;
}
