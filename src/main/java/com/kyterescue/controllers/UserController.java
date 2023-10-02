package com.kyterescue.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @GetMapping("/signup")
    public String viewSignup() {
        return "users/signup";
    }

    @PostMapping("/signup")
    public String newUserSignup() {
        return "users/signup";
    }

    @GetMapping("/login")
    public String viewLogin() {
        return "users/login";
    }

    @PostMapping("/login")
    public String userLogsIn() {
        return "users/login";
    }

    @GetMapping("/profile")
    public String viewProfile() {
        return "users/profile";
    }

    @PostMapping("/profile")
    public String editProfile() {
        return "users/profile";
    }

    @GetMapping("/logout")
    public String viewLogout() {
        return "users/logout";
    }
}
