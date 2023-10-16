package com.kyterescue.services;
import com.kyterescue.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.*;

@Service
public class EmailService {

    public final JavaMailSender emailSender;
    @Value("${spring.mail.from}")
    private String from;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void prepareAndSend(String subject, String body, User user) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(user.getEmail());
        msg.setSubject(subject);
        msg.setText(body);
        try{
            this.emailSender.send(msg);
        } catch(MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
