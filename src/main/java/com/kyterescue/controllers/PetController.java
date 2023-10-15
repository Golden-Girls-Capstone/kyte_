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
    BadgeRespository badgeDao;
    AuthenticationService authenticationService;
    DashboardFosterDisplayService dashboardFosterDisplayService;
    PetMapperService mapperService;

    PetController(UserRepository usersDao,BadgeRespository badgeDao, PetRepository petsDao, FosterPetRepository fostersDao, ReviewRepository reviewsDao, AuthenticationService authenticationService, DashboardFosterDisplayService dashboardFosterDisplayService, PetMapperService mapperService) {
        this.usersDao = usersDao;
        this.petsDao = petsDao;
        this.fostersDao = fostersDao;
        this.badgeDao = badgeDao;
        this.reviewsDao = reviewsDao;
        this.authenticationService = authenticationService;
        this.dashboardFosterDisplayService = dashboardFosterDisplayService;
        this.mapperService = mapperService;
    }

    @GetMapping("/dashboard")

    public String viewDashboard(Model model, @CurrentSecurityContext(expression = "authentication?.name")String username) throws JsonProcessingException {
        User user = usersDao.findByUsername(username);
        model.addAttribute("current", dashboardFosterDisplayService.grabCurrentFosterAsPet(user));
        model.addAttribute("user", user);
        model.addAttribute("fosters", dashboardFosterDisplayService.grabFosterHistory(user));
        model.addAttribute("favorites", user.getFavorites());
        model.addAttribute("badges", user.getBadges());
        model.addAttribute("reviews", user.getReviews());
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

    @GetMapping("pets/{id}/view")
    public String viewPetProfile(@PathVariable String id, Model model) {
        Pet petToView = petsDao.getPetById(Long.parseLong(id));
        model.addAttribute("pet", petToView);
//        model.addAttribute("reviews", reviews);
        return "pets/petprofile";
    }


    @PostMapping("/dashboard/review")
    public String createReview(@ModelAttribute Review review, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        User user = usersDao.findByUsername(username);
        Pet currentFoster = dashboardFosterDisplayService.grabCurrentFosterAsPet(user);
        FosterPet unsetCurrentFoster = dashboardFosterDisplayService.grabCurrentFosterAsFosterPet(user);
        review.setUser(usersDao.findByUsername(username));
        review.setPet(currentFoster);
        reviewsDao.save(review);
        unsetCurrentFoster.setStatus(false);
        fostersDao.save(unsetCurrentFoster);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/review/delete/{id}")
    public String deleteReview(@PathVariable long id) {
        reviewsDao.delete(reviewsDao.findById(id).get());
        return "redirect:/dashboard";
    }
}
