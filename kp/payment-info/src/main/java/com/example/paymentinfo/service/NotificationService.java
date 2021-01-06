package com.example.paymentinfo.service;


import com.example.paymentinfo.domain.Client;

public interface NotificationService {

    void sendEmailVerificationNotification(Client client);
}
