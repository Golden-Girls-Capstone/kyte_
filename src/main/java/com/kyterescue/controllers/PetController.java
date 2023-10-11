package com.kyterescue.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyterescue.entities.*;
import com.kyterescue.services.AuthenticationService;
import com.kyterescue.services.DashboardFosterDisplayService;
//import com.kyterescue.services.GrabApiDataService;
import org.springframework.data.annotation.Id;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.security.core.annotation.CurrentSecurityContext;
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
    ReviewRepository reveiwsDao;
    AuthenticationService authenticationService;
    DashboardFosterDisplayService dashboardFosterDisplayService;

    PetController(UserRepository usersDao, PetRepository petsDao, FosterPetRepository fostersDao, ReviewRepository reviewsDao, AuthenticationService authenticationService, DashboardFosterDisplayService dashboardFosterDisplayService) {
        this.usersDao = usersDao;
        this.petsDao = petsDao;
        this.fostersDao = fostersDao;
        this.reveiwsDao = reviewsDao;
        this.authenticationService = authenticationService;
        this.dashboardFosterDisplayService = dashboardFosterDisplayService;
    }

    @GetMapping("/dashboard")
    public String viewDashboard(Model model) throws JsonProcessingException {

        //this is showing stuff on the dashboard

        FosterPet currentFoster = dashboardFosterDisplayService.grabCurrentFoster(model);
        List<Pet> petHistory = dashboardFosterDisplayService.grabPetHistory(model);
        model.addAttribute("current", currentFoster);
        model.addAttribute("pets", petHistory);
//        long userId = authenticationService.grabAuthenticationUserDetails(model).getId();
        long userId = 7L;
        User user = usersDao.getUserById(userId);
        model.addAttribute("profile", user);
        List<FosterPet> fosterHistory = dashboardFosterDisplayService.grabFosterHistory(model);
        model.addAttribute("fosters", fosterHistory);

        //this for the history of the reviews the user has made
//        List<Review> reviewHistory = dashboardFosterDisplayService
        model.addAttribute("review", new Review());

        return "pets/dashboard";
    }

    @PostMapping("/dashboard")
    public String editDashboard(Model model) {
        return "pets/dashboard";
    }

//    @PostMapping("pet/review/{fosterId}")
//    public String editPetReview(@PathVariable long fosterId, @RequestParam("foster_review") String review){
//        System.out.println("inside pet review");
//        FosterPet foster = fostersDao.findById(fosterId).get();
//        foster.setFoster_reviews(review);
//        fostersDao.save(foster);
//
//        return "redirect:/dashboard";
//    }

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


    @PostMapping("/dashboard/review")
    public String createReview(@RequestParam Review review, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        Review newReview = new Review();
        User user = usersDao.findByUsername(username);
        FosterPet currentFoster = dashboardFosterDisplayService.grabCurrentFoster(model);
        newReview.setBody(review.getBody());
        newReview.setUser(user);
        newReview.setFosterPet(currentFoster);
        reveiwsDao.save(newReview);
        return "pets/dashboard";
    }


}
