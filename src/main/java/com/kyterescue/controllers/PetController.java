package com.kyterescue.controllers;

import com.kyterescue.entities.Pet;
import com.kyterescue.entities.PetRepository;
import com.kyterescue.entities.UserRepository;
import com.kyterescue.services.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PetController {

    UserRepository usersDao;
    PetRepository petsDao;
    AuthenticationService authenticationService;

    PetController(UserRepository usersDao, PetRepository petsDao, AuthenticationService authenticationService) {
        this.usersDao = usersDao;
        this.petsDao = petsDao;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {
        authenticationService.grabAuthenticatedUserDetails(model);
        return "pets/dashboard";
    }

    @PostMapping("/dashboard")
    public String editDashboard(Model model) {
        authenticationService.grabAuthenticatedUserDetails(model);
        return "pets/dashboard";
    }

    @GetMapping("/browse")
    public String viewBrowse() {
        return "pets/browse";
    }

    @PostMapping("/browse")
    public String fosterOrSave(Model model) {
        authenticationService.grabAuthenticatedUserDetails(model);
        return "pets/browse";
    }

    @GetMapping("pets/{id}/view")
    public String viewPetProfile(@PathVariable String id, Model model) {
        Pet petToView = petsDao.getPetById(Long.parseLong(id));
        model.addAttribute("pet", petToView);
        return "pets/petprofile";
    }
}
