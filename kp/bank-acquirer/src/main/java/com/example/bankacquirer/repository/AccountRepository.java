package com.example.bankacquirer.repository;

import org.springframework.stereotype.Repository;
import com.example.bankacquirer.domain.Account;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
