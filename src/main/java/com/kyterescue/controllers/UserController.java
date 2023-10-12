package com.kyterescue.controllers;

import com.kyterescue.entities.*;
import com.kyterescue.services.AuthenticationService;
import com.kyterescue.services.CheckForUniqueEmailService;
import com.kyterescue.services.CheckForUniqueUsernameService;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    UserRepository usersDao;
    FosterPetRepository fosterPetDao;
    PasswordEncoder passwordEncoder;
    AuthenticationService authenticationService;
    CheckForUniqueUsernameService checkUsername;
    CheckForUniqueEmailService checkEmail;
    UserController(UserRepository usersDao, FosterPetRepository fosterPetDao, PasswordEncoder passwordEncoder, AuthenticationService authenticationService, CheckForUniqueUsernameService checkUsername, CheckForUniqueEmailService checkEmail) {
        this.usersDao = usersDao;
        this.fosterPetDao = fosterPetDao;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
        this.checkUsername = checkUsername;
        this.checkEmail = checkEmail;
    }
    @GetMapping("/sign-up")
    public String viewSignup(Model model) {
        model.addAttribute("user", new User());
        return "users/signup";
    }
    @PostMapping("/sign-up")
    public String newUserSignup(@ModelAttribute User user, Model model) {
        if(checkUsername.check(user.getUsername()) && checkEmail.check(user.getEmail())) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        usersDao.save(user);
        return "redirect:/login";
        } else {
            if(!checkUsername.check(user.getUsername())) {
                model.addAttribute("userNotUnique", true);
            } else if(!checkEmail.check(user.getEmail())) {
                model.addAttribute("emailNotUnique", true);
            } else if(!checkUsername.check(user.getUsername()) && !checkEmail.check(user.getEmail())) {
                model.addAttribute("bothNotUnique", true);
            }
            return "redirect:/sign-up";
        }
    }

    @GetMapping("/login")
    public String viewLogin() {
        return "users/login";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@ModelAttribute User user, @CurrentSecurityContext(expression = "authentication?.name") String username) {
        User userToEdit = usersDao.findByUsername(username);
        userToEdit.setEmail(user.getEmail());
        userToEdit.setUsername(user.getUsername());
        userToEdit.setZipcode(user.getZipcode());
        usersDao.save(userToEdit);
        return "redirect:/dashboard";
    }
    @GetMapping("/logout")
    public String viewLogout() {
        return "users/logout";
    }
}
