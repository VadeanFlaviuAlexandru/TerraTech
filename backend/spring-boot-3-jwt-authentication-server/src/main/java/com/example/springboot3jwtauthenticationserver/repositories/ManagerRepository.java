package com.example.springboot3jwtauthenticationserver.repositories;

import com.example.springboot3jwtauthenticationserver.models.Manager;
import com.example.springboot3jwtauthenticationserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Manager findByEmail(String email);
}
