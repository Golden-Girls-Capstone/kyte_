package com.kyterescue.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PetController {
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
