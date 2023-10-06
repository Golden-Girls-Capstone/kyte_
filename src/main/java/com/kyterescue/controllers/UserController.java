package com.kyterescue.controllers;

import com.kyterescue.entities.User;
import com.kyterescue.entities.UserRepository;
import com.kyterescue.services.AuthenticationService;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/profile/edit")
    public String editProfile(@ModelAttribute User user, @CurrentSecurityContext(expression = "authentication?.name") String username) {
//        authenticationService.grabAuthenticationUserDetails(model);
//        long userId = authenticationService.grabAuthenticationUserDetails(model).getId();
//        User user = usersDao.getUserById(userId);
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

//    @DeleteMapping("user/{id}")
//    String deleteUser(@PathVariable Long id){
//        if(!usersDao.existsById(id)){
//            throw new RuntimeException();
//        }
//        usersDao.deleteById(id);
//        return "redirect:/login";
//    }

    @PostMapping("/profile/edit/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        usersDao.delete(usersDao.findById(id).get());
        return "redirect:/login";
    }




}
