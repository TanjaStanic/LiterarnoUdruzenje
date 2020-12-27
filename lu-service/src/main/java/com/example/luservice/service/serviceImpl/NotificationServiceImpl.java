package com.example.luservice.service.serviceImpl;

import com.example.luservice.model.User;
import com.example.luservice.service.NotificationService;
import com.example.luservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    private String endpoint;
    private JavaMailSender mailSender;
    private UserService userService;

    @Autowired
    public NotificationServiceImpl(@Value("${verify.email.endpoint}") String endpoint, JavaMailSender mailSender, UserService userService) {
        this.endpoint = endpoint;
        this.mailSender = mailSender;
        this.userService = userService;
    }

    @Override
    public void sendEmailVerificationNotification(User user) {
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user = userService.update(user);

        String subject = "Email verification";
        String emailText = "In order to be able to log in, you must verify your email. Click on this link to verify \n";
        try {
            sendEmailNotification(user, subject, emailText + endpoint + token);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email.");
        }

    }

    @Async
    public void sendEmailNotification(User user, String subject, String notification) {
        mailSender.send(composeEmail(user.getEmail(), subject, notification));
    }


    private SimpleMailMessage composeEmail(String recipient, String subject, String notification) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(recipient);
        mail.setSubject(subject);
        mail.setText(notification);
        return mail;
    }
}
