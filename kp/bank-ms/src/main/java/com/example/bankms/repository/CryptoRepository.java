package com.example.bankms.repository;

import com.example.bankms.domain.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoRepository extends JpaRepository<Crypto, Long> {

    Crypto findByText(String text);
}
