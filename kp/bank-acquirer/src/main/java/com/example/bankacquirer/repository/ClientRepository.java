package com.example.bankacquirer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bankacquirer.domain.Account;
import com.example.bankacquirer.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findOneByAccount(Account account);

    Client findOneByMerchantID(String merchantId);

    Client save(Client client);

    Client findByEmail(String email);
}
