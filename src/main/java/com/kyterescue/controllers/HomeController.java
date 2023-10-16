package com.kyterescue.controllers;
import com.kyterescue.services.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    AuthenticationService authenticationService;

    HomeController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/")
    public String viewLanding(Model model) {
        return "landing/landing";
    }

    @GetMapping("/about")
    public String viewAboutUs(Model model) {
        authenticationService.grabAuthenticationUserDetails(model);
        return "home/about";
    }
}
