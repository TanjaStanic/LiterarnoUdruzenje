package com.example.paymentinfo.service;


import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.dto.ClientRegistrationDto;

public interface AuthService {

    Client registerClient(ClientRegistrationDto newClient);

    void verifyEmail(String token);

}
