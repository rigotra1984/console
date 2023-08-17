package com.rigoberto.console.controllers;

import com.rigoberto.console.dtos.UserDto;
import org.slf4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;

public class BaseController {
    protected Logger logger;

    public BaseController(Logger logger) {
        this.logger = logger;
    }

    public UserDto getUser() {
        UserDto user = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Map<String, Object> details = (Map<String, Object>) authentication.getDetails();
            String sid = (String) details.get("sid");
            String username = (String) details.get("preferred_username");
            String emailID = (String) details.get("email");
            String lastname = (String) details.get("family_name");
            String firstname = (String) details.get("given_name");
            Boolean emailVerified = (Boolean) details.get("email_verified");
            List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

            user = new UserDto();
            user.setSid(sid);
            user.setUsername(username);
            user.setEmail(emailID);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmailVerified(emailVerified);
            user.setRoles(roles);
        }

        return user;
    }
}
