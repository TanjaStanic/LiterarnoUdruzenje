package com.example.paypalms.service;

import com.example.paypalms.domain.Crypto;

public interface CryptoService {

    void save(Crypto crypto);

    Crypto findByText(String text);

}
