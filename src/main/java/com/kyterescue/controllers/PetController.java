package com.kyterescue.controllers;

import com.kyterescue.entities.Pet;
import com.kyterescue.entities.PetRepository;
import com.kyterescue.services.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PetController {

    PetRepository petsDao;
    AuthenticationService authenticationService;

    PetController(PetRepository petsDao, AuthenticationService authenticationService) {
        this.petsDao = petsDao;
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

    @GetMapping("pets/{id}/view")
    public String viewPetProfile(@PathVariable String id, Model model) {
        Pet petToView = petsDao.getPetById(Long.parseLong(id));
        model.addAttribute("pet", petToView);
        return "pets/petprofile";
    }
}
