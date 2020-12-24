package com.example.paypalms.repository;


import com.example.paypalms.domain.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoRepository extends JpaRepository<Crypto, Long> {

    Crypto findByText(String text);
}
