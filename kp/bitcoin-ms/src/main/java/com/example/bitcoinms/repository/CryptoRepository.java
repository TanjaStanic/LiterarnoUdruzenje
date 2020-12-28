package com.example.bitcoinms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bitcoinms.domain.Crypto;

@Repository
public interface CryptoRepository extends JpaRepository<Crypto, Long> {

    Crypto findByText(String text);
}
