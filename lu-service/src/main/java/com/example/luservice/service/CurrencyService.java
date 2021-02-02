package com.example.luservice.service;




import com.example.luservice.model.Currency;

import java.util.Set;

public interface CurrencyService {

    Currency insert(Currency newCurrency);

    Currency update(Currency updated);

    void delete(Currency currency);

    Currency findById(Long id);

    void deleteById(Long id);

    Set<Currency> getAll();

    Currency findByCode(String code);
}
