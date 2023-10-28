package com.example.springboot3jwtauthenticationserver.repositories;

import com.example.springboot3jwtauthenticationserver.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
