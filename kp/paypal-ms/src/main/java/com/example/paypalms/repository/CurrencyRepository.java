package com.example.paypalms.repository;


import com.example.paypalms.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findByCode(String code);
}
