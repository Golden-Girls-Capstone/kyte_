package com.kyterescue.controllers;

import com.kyterescue.entities.User;
import com.kyterescue.entities.UserRepository;
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

    UserController(UserRepository usersDao, PasswordEncoder passwordEncoder) {
        this.usersDao = usersDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/signup")
    public String viewSignup(Model model) {
        model.addAttribute("user", new User());
        return "users/signup";
    }

    @PostMapping("/signup")
    public String newUserSignup(@ModelAttribute User user) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        usersDao.save(user);
        return "users/signup";
    }

    @GetMapping("/login")
    public String viewLogin() {
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
