package com.kyterescue.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyterescue.entities.*;
import com.kyterescue.services.AuthenticationService;
import com.kyterescue.services.DashboardFosterDisplayService;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PetController {

    UserRepository usersDao;
    PetRepository petsDao;
    FosterPetRepository fostersDao;
    AuthenticationService authenticationService;
    DashboardFosterDisplayService dashboardFosterDisplayService;

    PetController(UserRepository usersDao, PetRepository petsDao, FosterPetRepository fostersDao, AuthenticationService authenticationService, DashboardFosterDisplayService dashboardFosterDisplayService) {
        this.usersDao = usersDao;
        this.petsDao = petsDao;
        this.fostersDao = fostersDao;
        this.authenticationService = authenticationService;
        this.dashboardFosterDisplayService = dashboardFosterDisplayService;
    }

    @GetMapping("/dashboard")
    public String viewDashboard(Model model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Pet currentFoster = dashboardFosterDisplayService.grabCurrentFoster(model);
        List<Pet> fosterHistory = dashboardFosterDisplayService.grabFosterHistory(model);
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(fosterHistory));
        model.addAttribute("current", currentFoster);
        model.addAttribute("fosters", fosterHistory);
        return "pets/dashboard";
    }

    @PostMapping("/dashboard")
    public String editDashboard(Model model) {
        return "pets/dashboard";
    }

    @GetMapping("/browse")
    public String viewBrowse() {
        return "pets/browse";
    }

    @PostMapping("/browse")
    public String fosterOrSave(Model model) {
        authenticationService.grabAuthenticationUserDetails(model);
        return "pets/browse";
    }

//    @PostMapping("/browse/add-favorites")
//    public String addToFavorites(Model model) {
//
//    }

    @GetMapping("pets/{id}/view")
    public String viewPetProfile(@PathVariable String id, Model model) {
        Pet petToView = petsDao.getPetById(Long.parseLong(id));
        FosterPet fosterToView = fostersDao.findFosterPetByPetId(petToView);
        model.addAttribute("pet", petToView);
        model.addAttribute("foster", fosterToView);
        return "pets/petprofile";
    }
}
