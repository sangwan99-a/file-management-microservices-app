package com.example.auth.repository;

import com.example.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Method to find a user by email
    Optional<User> findByEmail(String email);

    // Method to check if a user exists by email
    Boolean existsByEmail(String email);
}
