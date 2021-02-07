package com.example.luservice.controller;

import com.example.luservice.dto.NewUserDto;
import com.example.luservice.model.User;
import com.example.luservice.security.JwtAuthenticationRequest;
import com.example.luservice.security.TokenUtils;
import com.example.luservice.security.UserTokenState;
import com.example.luservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4201", allowedHeaders = "*", maxAge = 3600)
public class AuthController {

    private AuthService authService;
    private AuthenticationManager authenticationManager;
    private TokenUtils tokenUtils;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, TokenUtils tokenUtils) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response)
            throws AuthenticationException, IOException {

        try {
            final Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user);
            long expiresIn = tokenUtils.getExpiredIn();
            UserTokenState userTokenState = new UserTokenState(jwt, expiresIn, user.getId());
            return ResponseEntity.ok().body(userTokenState);
        }catch (Exception exception){
            return (ResponseEntity<?>) ResponseEntity.badRequest().body(exception.getMessage());
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody NewUserDto newUserDto) {
        System.out.println(newUserDto.toString());
        try {
            authService.registerUser(newUserDto);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
        return ResponseEntity.ok().build();
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
