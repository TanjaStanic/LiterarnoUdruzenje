package com.example.paymentinfo.service;




import com.example.paymentinfo.domain.Currency;

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
