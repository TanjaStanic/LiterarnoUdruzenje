package com.example.bankacquirer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bankacquirer.domain.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long>{

}
