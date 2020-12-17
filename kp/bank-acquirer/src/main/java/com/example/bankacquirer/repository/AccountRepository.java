package com.example.bankacquirer.repository;

import org.springframework.stereotype.Repository;
import com.example.bankacquirer.domain.Account;
import com.example.bankacquirer.domain.Card;
import com.example.bankacquirer.domain.Client;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findOneById(Long id);
	Account findOneByOwner(Client client);
	Account save(Account account);
}
