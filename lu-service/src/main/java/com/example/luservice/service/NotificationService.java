package com.example.luservice.service;

import com.example.luservice.model.User;

public interface NotificationService {

    void sendEmailVerificationNotification(User user);
}
