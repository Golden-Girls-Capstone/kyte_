package com.kyterescue.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kyterescue.entities.*;
import com.kyterescue.services.AssignUserBadgesService;
import com.kyterescue.services.AuthenticationService;
import com.kyterescue.services.DashboardFosterDisplayService;
import com.kyterescue.services.PetMapperService;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.time.LocalDate;

@Controller
public class PetController {

    UserRepository usersDao;
    PetRepository petsDao;
    FosterPetRepository fostersDao;
    ReviewRepository reviewsDao;
    BadgeRepository badgeDao;
    AuthenticationService authenticationService;
    DashboardFosterDisplayService dashboardFosterDisplayService;
    PetMapperService mapperService;
    AssignUserBadgesService badgesService;

    PetController(
            UserRepository usersDao,
            BadgeRepository badgeDao,
            PetRepository petsDao,
            FosterPetRepository fostersDao,
            ReviewRepository reviewsDao,
            AuthenticationService authenticationService,
            DashboardFosterDisplayService dashboardFosterDisplayService,
            PetMapperService mapperService,
            AssignUserBadgesService badgesService
    ){
        this.usersDao = usersDao;
        this.petsDao = petsDao;
        this.fostersDao = fostersDao;
        this.badgeDao = badgeDao;
        this.reviewsDao = reviewsDao;
        this.authenticationService = authenticationService;
        this.dashboardFosterDisplayService = dashboardFosterDisplayService;
        this.mapperService = mapperService;
        this.badgesService = badgesService;
    }

    @GetMapping("/dashboard")

    public String viewDashboard(Model model, @CurrentSecurityContext(expression = "authentication?.name")String username) throws JsonProcessingException {
        User user = authenticationService.grabAuthenticationUserDetails(model);
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
        User user = usersDao.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("searchForm", new SearchForm());
        model.addAttribute("foster", new FosterPet());
        return "pets/browse";
    }
    @GetMapping("pets/{id}/view")
    public String viewPetProfile(@PathVariable String id, Model model) {
        Pet petToView = petsDao.getPetById(Long.parseLong(id));
        model.addAttribute("pet", petToView);
        return "pets/petprofile";
    }
    @PostMapping("/dashboard/review")
    public String createReview(@ModelAttribute Review review, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        User user = authenticationService.grabAuthenticationUserDetails(model);
        Pet currentFoster = dashboardFosterDisplayService.grabCurrentFosterAsPet(user);
        FosterPet unsetCurrentFoster = dashboardFosterDisplayService.grabCurrentFosterAsFosterPet(user);
        review.setUser(user);
        review.setPet(currentFoster);
        reviewsDao.save(review);
        unsetCurrentFoster.setStatus(false);
        unsetCurrentFoster.setEnd_date(LocalDate.now());
        fostersDao.save(unsetCurrentFoster);
        badgesService.assignBadgeToUser(user, badgesService.getBadgeForUser(currentFoster));
        usersDao.save(user);
        return "redirect:/dashboard";
    }
    @PostMapping("/dashboard/review/delete/{id}")
    public String deleteReview(@PathVariable long id) {
        reviewsDao.delete(reviewsDao.findById(id).get());
        return "redirect:/dashboard";
    }
}
