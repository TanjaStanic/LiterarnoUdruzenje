package com.example.bankms.service;

import com.example.bankms.domain.Crypto;

public interface CryptoService {

    void save(Crypto crypto);

    Crypto findByText(String text);

}
