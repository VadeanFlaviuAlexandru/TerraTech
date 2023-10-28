package com.example.springboot3jwtauthenticationserver.repositories;

import com.example.springboot3jwtauthenticationserver.models.Manager;
import com.example.springboot3jwtauthenticationserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    List<User> findByManagerId(Long managerId);
}
