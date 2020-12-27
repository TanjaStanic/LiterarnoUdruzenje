package com.example.luservice.service.serviceImpl;

import com.example.luservice.model.User;
import com.example.luservice.service.CustomUserDetailsService;
import com.example.luservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private UserService userService;

    @Autowired
    public CustomUserDetailsServiceImpl(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user;
        try {
            user = userService.findByUsername(s);
        }catch (Exception exception){
            throw new UsernameNotFoundException(MessageFormat.format("No user with the username: {0}", s));
        }

        return user;
    }
}
