package com.kyterescue.controllers;
import com.kyterescue.entities.*;
import com.kyterescue.services.AuthenticationService;
import com.kyterescue.services.CheckForUniqueService;
import com.kyterescue.services.EmailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    UserRepository usersDao;
    FosterPetRepository fosterPetDao;
    BadgeRepository badgesDao;
    PasswordEncoder passwordEncoder;
    AuthenticationService authenticationService;
    CheckForUniqueService checkService;
    EmailService emailService;

    UserController(
            UserRepository usersDao,
            BadgeRepository badgesDao,
            FosterPetRepository fosterPetDao,
            PasswordEncoder passwordEncoder,
            AuthenticationService authenticationService,
            CheckForUniqueService checkService,
            EmailService emailService
    ){
        this.usersDao = usersDao;
        this.fosterPetDao = fosterPetDao;
        this.badgesDao = badgesDao;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
        this.checkService = checkService;
        this.emailService = emailService;
    }

    @GetMapping("/sign-up")
    public String viewSignup(Model model) {
        model.addAttribute("user", new User());
        return "users/signup";
    }
    @PostMapping("/sign-up")
    public String newUserSignup(@ModelAttribute User user, Model model) {
        if(checkService.checkUsername(user.getUsername()) && checkService.checkEmail(user.getEmail())) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        usersDao.save(user);
        emailService.prepareAndSend("Welcome to KyteRescue!", "Thanks for joining KyteRescue! We'll have your background check sent to you soon!", user);
        return "redirect:/login";
        } else {
            if(!checkService.checkUsername(user.getUsername())) {
                model.addAttribute("userNotUnique", true);
            } else if(!checkService.checkEmail(user.getEmail())) {
                model.addAttribute("emailNotUnique", true);
            } else if(!checkService.checkUsername(user.getUsername()) && !checkService.checkEmail(user.getEmail())) {
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
    public void editProfile(@ModelAttribute User user, Model model, HttpServletRequest request) throws ServletException {
        User userToEdit = authenticationService.grabAuthenticationUserDetails(model);
            userToEdit.setUsername(user.getUsername());
            userToEdit.setEmail(user.getEmail());
            userToEdit.setZipcode(user.getZipcode());
            usersDao.save(userToEdit);
            request.logout();
    }
}
