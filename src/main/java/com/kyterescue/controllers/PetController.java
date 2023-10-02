package com.kyterescue.controllers;

import com.kyterescue.entities.Pet;
import com.kyterescue.services.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PetController {

    AuthenticationService authenticationService;

    PetController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/dashboard")
    public String viewDashboard() {
        return "pets/dashboard";
    }

    @PostMapping("/dashboard")
    public String editDashboard() {
        return "pets/dashboard";
    }

    @GetMapping("/browse")
    public String viewBrowse() {
        return "pets/browse";
    }

    @PostMapping("/browse")
    public String fosterOrSave() {
        return "pets/browse";
    }
}
