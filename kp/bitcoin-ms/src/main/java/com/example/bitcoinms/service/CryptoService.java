package com.example.bitcoinms.service;

import org.springframework.stereotype.Service;

import com.example.bitcoinms.domain.Crypto;

@Service
public interface CryptoService {
	
	 void save(Crypto crypto);
	 Crypto findByText(String text);


}
