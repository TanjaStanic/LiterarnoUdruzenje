package com.example.bankms.repository;

import com.example.bankms.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {

    Bank findByUniqueBankNum(String bankNumber);
}
