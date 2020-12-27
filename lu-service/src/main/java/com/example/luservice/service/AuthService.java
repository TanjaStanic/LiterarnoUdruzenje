package com.example.luservice.service;

import com.example.luservice.dto.NewUserDto;
import com.example.luservice.model.User;

public interface AuthService {

    User registerUser(NewUserDto newUserDto);

    void verifyEmail(String token);
}
