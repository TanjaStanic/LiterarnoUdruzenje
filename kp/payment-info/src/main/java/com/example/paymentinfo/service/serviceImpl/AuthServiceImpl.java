package com.example.paymentinfo.service.serviceImpl;


import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.dto.ClientRegistrationDto;
import com.example.paymentinfo.repository.RoleRepository;
import com.example.paymentinfo.service.AuthService;
import com.example.paymentinfo.service.ClientService;
import com.example.paymentinfo.service.NotificationService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {
    private ClientService clientService;
    private PasswordEncoder passwordEncoder;
    private NotificationService notificationService;
    private RoleRepository roleRepository;
    private static String regex = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=!~&*])"
            + "(?=\\S+$).{8,20}$";

    public static final Pattern EMAIL_VERIFICATION =
            Pattern.compile("[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}", Pattern.CASE_INSENSITIVE);

    public static final Pattern PASSWORD_VERIFICATION =
            Pattern.compile(regex);

    public AuthServiceImpl(ClientService clientService, PasswordEncoder passwordEncoder,
                           NotificationService notificationService, RoleRepository roleRepository) {
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
        this.notificationService = notificationService;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public Client registerClient(ClientRegistrationDto clientRegistration) {
        if (!validateString(EMAIL_VERIFICATION, clientRegistration.getEmail())) {
            throw new RuntimeException("Invalid email!");
        }
        Client client = clientService.findByUsername(clientRegistration.getEmail());
        if (client != null) {
            throw new RuntimeException("Email already in use!");
        }

        if (!clientRegistration.getPassword().equals(clientRegistration.getPasswordConfirm())) {
            throw new RuntimeException("Passwords do not match!");
        }
        if (!validateString(PASSWORD_VERIFICATION, clientRegistration.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        Client user = new Client();
        user.setEmail(clientRegistration.getEmail());
        user.setName(StringUtils.capitalize(clientRegistration.getName()));
        user.setPassword(passwordEncoder.encode(clientRegistration.getPassword()));
        user.setEnabled(false);
        user.setRole(roleRepository.findByName("ROLE_CLIENT"));
        user.setCompanyRegistrationNumber(clientRegistration.getCompanyRegistrationNumber());
        user.setTaxIdentificationNumber(clientRegistration.getTaxIdentificationNumber());

        user = clientService.insert(user);
        // TODO Remove comment
        //notificationService.sendEmailVerificationNotification(user);
        return user;
    }

    @Override
    public void verifyEmail(String token) {
        Client user = clientService.findByToken(token);
        if (!(user.getToken().equals(token))) {
            throw new RuntimeException("Invalid token.");
        }
        user.setEnabled(true);
        user.setToken(null);
        clientService.update(user);
    }

    public static boolean validateString(Pattern pattern, String stringToValidate) {
        Matcher matcher = pattern.matcher(stringToValidate);
        return matcher.find();
    }
}
