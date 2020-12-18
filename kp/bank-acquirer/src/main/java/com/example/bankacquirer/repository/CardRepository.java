package com.example.bankacquirer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.bankacquirer.domain.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{
	Card findOneByPan(String pan);
}
