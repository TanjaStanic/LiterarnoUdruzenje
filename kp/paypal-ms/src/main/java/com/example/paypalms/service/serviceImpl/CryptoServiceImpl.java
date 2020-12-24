package com.example.paypalms.service.serviceImpl;

import com.example.paypalms.domain.Crypto;
import com.example.paypalms.repository.CryptoRepository;
import com.example.paypalms.service.CryptoService;
import org.springframework.stereotype.Service;

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
