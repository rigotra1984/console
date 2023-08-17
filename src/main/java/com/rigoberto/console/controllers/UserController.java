package com.rigoberto.console.controllers;

import com.rigoberto.console.dtos.UserDto;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    public UserController() {
        super(LoggerFactory.getLogger(UserController.class));
    }

    @GetMapping("me")
    public UserDto getMe() {
        return getUser();
    }
}
