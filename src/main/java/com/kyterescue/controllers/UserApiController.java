package com.kyterescue.controllers;

import com.kyterescue.entities.UserRepository;
import com.kyterescue.services.AuthenticationService;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserApiController {
    private UserRepository usersDao;
    private AuthenticationService authService;

    UserApiController(UserRepository usersDao, AuthenticationService authService) {
        this.usersDao = usersDao;
        this.authService = authService;
    }

    @GetMapping(value = "/dashboard/send/validation/error", produces = "text/plain")
    public String sendValidationError(){
        return "validationError";
    }

}
