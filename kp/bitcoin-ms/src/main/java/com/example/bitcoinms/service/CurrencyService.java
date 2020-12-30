package com.example.bitcoinms.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.bitcoinms.domain.Currency;


@Service
public interface CurrencyService {

    Currency insert(Currency newCurrency);

    Currency update(Currency updated);

    void delete(Currency currency);

    Currency findById(Long id);

    void deleteById(Long id);

    Set<Currency> getAll();

    Currency findByCode(String code);
}
