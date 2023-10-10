package com.kyterescue.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyterescue.entities.*;
import com.kyterescue.services.AuthenticationService;
import com.kyterescue.services.DashboardFosterDisplayService;
//import com.kyterescue.services.GrabApiDataService;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

        Pet currentFoster = dashboardFosterDisplayService.grabCurrentFoster(model);
        List<Pet> petHistory = dashboardFosterDisplayService.grabPetHistory(model);
        model.addAttribute("current", currentFoster);
        model.addAttribute("pets", petHistory);
        long userId = authenticationService.grabAuthenticationUserDetails(model).getId();
//        long userId = 7L;
        User user = usersDao.getUserById(userId);
        model.addAttribute("profile", user);
        List<FosterPet> fosterHistory = dashboardFosterDisplayService.grabFosterHistory(model);
        model.addAttribute("fosters", fosterHistory);

        return "pets/dashboard";
    }

    @PostMapping("/dashboard")
    public String editDashboard(Model model) {
        return "pets/dashboard";
    }

    @GetMapping("/browse")
    public String viewBrowse(Model model) throws IOException {
        model.addAttribute("searchForm", new SearchForm());
        return "pets/browse";
    }

    @PostMapping("/browse")
    public String fosterOrSave(Model model) {
        return "pets/browse";
    }

    @GetMapping("pets/{id}/view")
    public String viewPetProfile(@PathVariable String id, Model model) {
        Pet petToView = petsDao.getPetById(Long.parseLong(id));
        List<String> reviews = fostersDao.findReviewsOfFoster(petToView.getId());
        model.addAttribute("pet", petToView);
        model.addAttribute("reviews", reviews);
        return "pets/petprofile";
    }
}
