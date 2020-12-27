package com.example.luservice.service;

import com.example.luservice.model.User;

public interface UserService {
    User findByUsername(String username);

    User insert(User user);

    User findByIdAndEnabled(Long id, boolean enabled);

    User update(User edited);

    User findByToken(String token);
}
