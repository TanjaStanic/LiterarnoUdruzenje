package com.example.luservice.service.serviceImpl;

import com.example.luservice.dto.NewUserDto;
import com.example.luservice.model.User;
import com.example.luservice.repository.RoleRepository;
import com.example.luservice.service.AuthService;
import com.example.luservice.service.NotificationService;
import com.example.luservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private NotificationService notificationService;
    private static String regex = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=!~&*])"
            + "(?=\\S+$).{8,20}$";

    public static final Pattern EMAIL_VERIFICATION =
            Pattern.compile("[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}", Pattern.CASE_INSENSITIVE);

    public static final Pattern PASSWORD_VERIFICATION =
            Pattern.compile(regex);

    @Autowired
    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, RoleRepository roleRepository, NotificationService notificationService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public User registerUser(NewUserDto newUserDto) {
        if (!validateString(EMAIL_VERIFICATION, newUserDto.getEmail())) {
            throw new RuntimeException("Invalid email!");
        }
        boolean userExists = true;
        try{
            userService.findByUsername(newUserDto.getEmail());
        }catch (RuntimeException exception){
            userExists = false;
        }finally {
            if (userExists){
                throw new RuntimeException("Email already in use.");
            }
        }
        if (!newUserDto.getPassword().equals(newUserDto.getPasswordConfirm())) {
            throw new RuntimeException("Passwords do not match!");
        }
        if (!validateString(PASSWORD_VERIFICATION, newUserDto.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        User user = new User();
        user.setEmail(newUserDto.getEmail());
        user.setName(StringUtils.capitalize(newUserDto.getName()));
        user.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        user.setEnabled(false);
        user.setRole(roleRepository.findByName("READER"));
        user.setCity(newUserDto.getCity());
        user.setCountry(newUserDto.getCountry());

        user = userService.insert(user);
        notificationService.sendEmailVerificationNotification(user);
        return user;
    }

    @Override
    public void verifyEmail(String token) {
        User user = userService.findByToken(token);
        if (!(user.getToken().equals(token))) {
            throw new RuntimeException("Invalid token.");
        }
        user.setEnabled(true);
        user.setToken(null);
        userService.update(user);
    }

    public static boolean validateString(Pattern pattern, String stringToValidate) {
        Matcher matcher = pattern.matcher(stringToValidate);
        return matcher.find();
    }
}
