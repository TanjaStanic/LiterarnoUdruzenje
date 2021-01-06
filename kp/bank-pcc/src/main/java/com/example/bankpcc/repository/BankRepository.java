package com.example.bankpcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bankpcc.domain.Bank;

public interface BankRepository extends JpaRepository<Bank, Long>{
	
	Bank findOneByUniqueBankNumber(String uniqueBankNumber);

}
