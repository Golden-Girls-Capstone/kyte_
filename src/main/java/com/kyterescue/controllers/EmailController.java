package com.kyterescue.controllers;

import com.kyterescue.entities.UserRepository;
import com.kyterescue.services.EmailService;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private EmailService emailService;
    private UserRepository usersDao;

    public EmailController(EmailService emailService, UserRepository usersDao) {
        this.emailService = emailService;
        this.usersDao = usersDao;
    }

    @GetMapping("/email")
    public String sendTest(@CurrentSecurityContext(expression = "authorization?.name") String username) {
        try{
            emailService.prepareAndSend("Test", "This is test body", usersDao.findByUsername(username));
            return "Email sent successfully";
        } catch(Exception e) {
            return "Failed to send test email: " + e.getMessage();
        }
    }
}
