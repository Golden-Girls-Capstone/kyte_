package com.kyterescue.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyterescue.entities.*;
import com.kyterescue.services.AuthenticationService;
import com.kyterescue.services.DashboardFosterDisplayService;
//import com.kyterescue.services.GrabApiDataService;
import com.kyterescue.services.PetMapperService;
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
    ReviewRepository reviewsDao;
    AuthenticationService authenticationService;
    DashboardFosterDisplayService dashboardFosterDisplayService;
    PetMapperService mapperService;

    PetController(UserRepository usersDao, PetRepository petsDao, FosterPetRepository fostersDao, ReviewRepository reviewsDao, AuthenticationService authenticationService, DashboardFosterDisplayService dashboardFosterDisplayService, PetMapperService mapperService) {
        this.usersDao = usersDao;
        this.petsDao = petsDao;
        this.fostersDao = fostersDao;
        this.reviewsDao = reviewsDao;
        this.authenticationService = authenticationService;
        this.dashboardFosterDisplayService = dashboardFosterDisplayService;
        this.mapperService = mapperService;
    }

    @GetMapping("/dashboard")
    public String viewDashboard(Model model, @CurrentSecurityContext(expression = "authentication?.name")String username) throws JsonProcessingException {
        User user = usersDao.findByUsername(username);
        List<Review> reviewHistory = dashboardFosterDisplayService.grabReviewHistory(user);
        FosterPet currentFoster = dashboardFosterDisplayService.grabCurrentFoster(model);
        List<Pet> petHistory = dashboardFosterDisplayService.grabPetHistory(model);
        List<FosterPet> fosterHistory = dashboardFosterDisplayService.grabFosterHistory(model);
        model.addAttribute("current", currentFoster);
        model.addAttribute("pets", petHistory);
        model.addAttribute("profile", user);
        model.addAttribute("fosters", fosterHistory);
        model.addAttribute("reviews", reviewHistory);
        model.addAttribute("review", new Review());
        return "pets/dashboard";
    }

    @PostMapping("/dashboard")
    public String editDashboard(Model model) {
        return "pets/dashboard";
    }

    @GetMapping("/browse")
    public String viewBrowse(Model model, @CurrentSecurityContext(expression = "authentication?.name") String username) throws IOException {
        User user= usersDao.findByUsername(username);
        model.addAttribute("user", user);

        model.addAttribute("searchForm", new SearchForm());
        model.addAttribute("foster", new FosterPet());
        return "pets/browse";
    }

    @PostMapping("/browse")
    public String fosterOrSave(@ModelAttribute(name = "foster") FosterPet fosterPet, @CurrentSecurityContext(expression = "authentication?.name") String username, @RequestParam(name = "petId") Long id, @RequestParam(name = "button") String button) throws IOException {
            System.out.println("inside browse");
        if(button.equals("foster")) {
            Pet petToFoster = mapperService.checkAndMapToPet(String.valueOf(id));
            FosterPet newFoster = mapperService.mapPetToFosterPet(fosterPet, usersDao.findByUsername(username), petToFoster);
            newFoster.setStart_date((LocalDate) fosterPet.getStart_date());
            newFoster.setEnd_date((LocalDate) fosterPet.getEnd_date());
            fostersDao.save(newFoster);
            return "pets/browse";

        } else if(button.equals("save")) {
            System.out.println("save pressed");
           Pet favoritePet = mapperService.checkAndMapToPet(String.valueOf(id));
           User user = usersDao.findByUsername(username);
           user.addFavorite(favoritePet);
           usersDao.save(user);
            System.out.println(user.getPets());
           return "pets/browse";
        } else {
            return "pets/browse";
        }
    }

    @GetMapping("pets/{id}/view")
    public String viewPetProfile(@PathVariable String id, Model model) {
        Pet petToView = petsDao.getPetById(Long.parseLong(id));
        model.addAttribute("pet", petToView);
//        model.addAttribute("reviews", reviews);
        return "pets/petprofile";
    }


    @PostMapping("/dashboard/review")
    public String createReview(@ModelAttribute Review review, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        System.out.println("save form");
        Review newReview = new Review();
        User user = usersDao.findByUsername(username);
        FosterPet currentFoster = dashboardFosterDisplayService.grabCurrentFoster(model);
        newReview.setBody(review.getBody());
        newReview.setUser(user);
        newReview.setFosterPet(currentFoster);
        reviewsDao.save(newReview);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/review/delete/{id}")
    public String deleteReview(@PathVariable long id) {
        reviewsDao.delete(reviewsDao.findById(id).get());
        return "redirect:/dashboard";
    }



}
