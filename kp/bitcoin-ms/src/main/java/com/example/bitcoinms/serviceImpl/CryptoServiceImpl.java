package com.example.bitcoinms.serviceImpl;

import org.springframework.stereotype.Service;

import com.example.bitcoinms.domain.Crypto;
import com.example.bitcoinms.repository.CryptoRepository;
import com.example.bitcoinms.service.CryptoService;

@Service
public class CryptoServiceImpl implements CryptoService {

    private final CryptoRepository cryptoRepository;

    public CryptoServiceImpl(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    @Override
    public void save(Crypto crypto) {
        cryptoRepository.save(crypto);
    }

    @Override
    public Crypto findByText(String text) {
        return cryptoRepository.findByText(text);
    }

	

	
}
