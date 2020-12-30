package com.example.bankms.service;

import org.springframework.stereotype.Service;

import com.example.bankms.domain.Crypto;

@Service
public interface CryptoService {

    void save(Crypto crypto);

    Crypto findByText(String text);

}
