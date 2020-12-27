package com.example.luservice.repository;

import com.example.luservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByIdAndEnabled(long id, boolean enabled);

    User findByToken(String token);
}
