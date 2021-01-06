package com.example.paymentinfo.service.serviceImpl;


import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.service.ClientService;
import com.example.paymentinfo.service.CustomUserDetailsService;
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
    private ClientService clientService;

    @Autowired
    public CustomUserDetailsServiceImpl(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, ClientService clientService) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.clientService = clientService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       Client client;
        try {
            client = clientService.findByUsername(s);
        }catch (Exception exception){
            throw new UsernameNotFoundException(MessageFormat.format("No user with the username: {0}", s));
        }

        return client;
    }
}
