package com.example.bankpcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bankpcc.domain.PccRequest;

public interface PccRequestRepository extends JpaRepository<PccRequest, Long>{

}
