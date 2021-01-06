package com.example.paymentinfo.service.serviceImpl;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.service.ClientService;
import com.example.paymentinfo.service.NotificationService;
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
    private ClientService clientService;

    @Autowired
    public NotificationServiceImpl(@Value("${verify.email.endpoint}") String endpoint, JavaMailSender mailSender, ClientService clientService) {
        this.endpoint = endpoint;
        this.mailSender = mailSender;
        this.clientService = clientService;
    }

    @Override
    public void sendEmailVerificationNotification(Client client) {
        String token = UUID.randomUUID().toString();
        client.setToken(token);
        client = clientService.update(client);

        String subject = "Email verification";
        String emailText = "In order to be able to log in, you must verify your email. Click on this link to verify \n";
        try {
            sendEmailNotification(client, subject, emailText + endpoint + token);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email.");
        }

    }

    @Async
    public void sendEmailNotification(Client user, String subject, String notification) {
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
