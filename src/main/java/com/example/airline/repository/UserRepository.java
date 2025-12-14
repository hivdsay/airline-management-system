package com.example.airline.repository;

import com.example.airline.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Spring Data JPA, metodun adını SQL’e çevirir
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    //findByEmail → SELECT * FROM users WHERE email = ?
    boolean existsByEmail(String email);
}
