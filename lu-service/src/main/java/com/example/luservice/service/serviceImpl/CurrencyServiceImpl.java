package com.example.luservice.service.serviceImpl;


import com.example.luservice.model.Currency;
import com.example.luservice.repository.CurrencyRepository;
import com.example.luservice.service.CurrencyService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Set;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Currency insert(Currency newCurrency) {
        return currencyRepository.save(newCurrency);
    }

    @Override
    public Currency update(Currency updated) {
        findById(updated.getId());
        return currencyRepository.save(updated);
    }

    @Override
    public void delete(Currency currency) {
        currencyRepository.delete(currency);
    }

    @Override
    public Currency findById(Long id) {
        Currency found = currencyRepository.findById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format("Currency with id {0} does not exist.", id)));
        return found;
    }

    @Override
    public void deleteById(Long id) {
        currencyRepository.deleteById(id);
    }

    @Override
    public Set<Currency> getAll() {
        return (Set<Currency>) currencyRepository.findAll();
    }

    @Override
    public Currency findByCode(String code) {
        return currencyRepository.findByCode(code);
    }
}
