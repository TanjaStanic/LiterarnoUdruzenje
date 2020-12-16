package com.example.paymentinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.paymentinfo.domain.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

}
