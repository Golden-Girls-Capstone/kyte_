package com.kyterescue.controllers;

import com.kyterescue.entities.User;
import com.kyterescue.entities.UserRepository;
import com.kyterescue.services.AuthenticationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    UserRepository usersDao;
    PasswordEncoder passwordEncoder;

    AuthenticationService authenticationService;

    UserController(UserRepository usersDao, PasswordEncoder passwordEncoder, AuthenticationService authenticationService) {
        this.usersDao = usersDao;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/sign-up")
    public String viewSignup(Model model) {
        model.addAttribute("user", new User());
        return "users/signup";
    }

    @PostMapping("/sign-up")
    public String newUserSignup(@ModelAttribute User user) {
        System.out.println("here");
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        usersDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String viewLogin() {
        return "users/login";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model) {
        authenticationService.grabAuthenticationUserDetails(model);
        return "users/profile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(Model model) {
        authenticationService.grabAuthenticationUserDetails(model);
        return "users/profile";
    }

    @GetMapping("/logout")
    public String viewLogout() {
        return "users/logout";
    }



}
