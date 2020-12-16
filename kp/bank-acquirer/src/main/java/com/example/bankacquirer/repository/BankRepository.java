package com.example.bankacquirer.repository;

import org.springframework.stereotype.Repository;
import com.example.bankacquirer.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long>{

}
