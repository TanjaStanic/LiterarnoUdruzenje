package com.example.paymentinfo.security;


import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private ClientService clientService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(ClientService clientService, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Client client;
        try {
            client = clientService.findByUsername(username);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw exception;
        }
        boolean matches = passwordEncoder.matches(password, ((Client) client).getPassword());
        if (!matches) {
            throw new RuntimeException("Invalid username or password.");
        }
        return new UsernamePasswordAuthenticationToken(client, client.getPassword(), client.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
