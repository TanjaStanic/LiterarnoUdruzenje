package com.example.bankms.service.serviceImpl;

import com.example.bankms.domain.Crypto;
import com.example.bankms.repository.CryptoRepository;
import com.example.bankms.service.CryptoService;
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
