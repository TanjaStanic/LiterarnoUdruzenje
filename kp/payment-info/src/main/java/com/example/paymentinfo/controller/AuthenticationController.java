package com.example.paymentinfo.controller;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.dto.ClientRegistrationDto;
import com.example.paymentinfo.security.JwtAuthenticationRequest;
import com.example.paymentinfo.security.TokenUtils;
import com.example.paymentinfo.security.UserTokenState;
import com.example.paymentinfo.service.AuthService;
import com.example.paymentinfo.utils.ClientValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;


@RestController
@RequestMapping("/auth")
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class AuthenticationController {

    private AuthService authService;
    private AuthenticationManager authenticationManager;
    private TokenUtils tokenUtils;
    private ClientValidator clientValidator;

    public AuthenticationController(AuthService authService, AuthenticationManager authenticationManager, TokenUtils tokenUtils, ClientValidator clientValidator) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.clientValidator = clientValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ClientRegistrationDto clientRegistrationDto, BindingResult bindingResult) {
        System.out.println(clientRegistrationDto);
        clientValidator.validate(clientRegistrationDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Client client = null;
        try {
            client = authService.registerClient(clientRegistrationDto);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("https://payment.center:8444/view/select-payment-methods/" + client.getId().toString());
    }

    @PostMapping("authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

        String password = authenticationRequest.getPassword();
        try {
            final Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            password
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Client client = (Client) authentication.getPrincipal();
            String token = tokenUtils.generateToken(client);
            long expiresIn = tokenUtils.getExpiredIn();
            UserTokenState userTokenState = new UserTokenState(token, expiresIn, client.getId());
            return ResponseEntity.ok(userTokenState);

        } catch (BadCredentialsException e) {
            log.error(MessageFormat.format("An unsuccessful attempt to log in with an email address: {0}", authenticationRequest.getUsername()));
            return ResponseEntity.badRequest().body("Invalid username or password!");
        }
    }

    @GetMapping("/verify-email/{token}")
    public ResponseEntity<?> verifyEmail(@PathVariable("token") String token) {
        try {
            authService.verifyEmail(token);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}
